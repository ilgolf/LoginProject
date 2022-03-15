# LoginProject

## 프로젝트 시작 이유

아무생각 없이 api를 만들게 되고 기능 구현에만 목적을 둔 개발을 멈추고 싶었습니다. 그래서 본 프로젝트에서는 불필요한 부분을 걷어내고 효과 적인 코드를 짜는데 집중하고, RESTful 규약을 이해하고 RestFul한 설계를 경험하기 위해 시작했습니다.

## [Wiki](https://github.com/ilgolf/LoginProject/wiki)

----------------------------------------------------------------------------------

## RestAPI 설계(간략)

- Create
  - path : /members
  - RequestMethod : Post
  - Success status : 201 Created
  - fail status : 4xx error, 5xx error


- Read
  - path : /members/email
  - RequestMethod : Get
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error


- Update
  - path : /members
  - RequestMethod : Put
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error


- Delete
  - path : /members
  - RequestMethod : Delete
  - Success status : 204 noContent
  - fail status : 4xx error, 5xx error

- ReadAll
  - path : /members
  - RequestMethod : GET
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error

## 특별한 패턴 적용

### 빌더 패턴

```java
public class MemberService {
  
    public Member signUp(MemberDTO memberDTO) throws DuplicateMemberException {
    if (memberRepository.findByEmail(memberDTO.getEmail()).orElse(null) != null) {
      throw new DuplicateMemberException("이미 가입된 정보입니다.");
    }
    Member member = Member.builder()
            .email(memberDTO.getEmail())
            .password(memberDTO.getPassword())
            .name(memberDTO.getName())
            .nickName(memberDTO.getNickName())
            .age(memberDTO.getAge())
            .roleType(RoleType.USER)
            .build();
    memberRepository.save(member);
    return member;
  }
}
```

회원가입 시에 member 필드에 많으 속성들이 담겨 있기 때문에 Builder 패턴을 이용하여 
구현하였다. 이렇게 되면 생성자 인자를 사용하였을 때보다 깔끔하게 확실히 넣어야 할 인자를
넣을 수 있게 되고 

setter를 사용하면 1회의 메서드 호출로 끝낼 수 없을 뿐더러 이것이 set으로만 표현되니 
직접 개발한 개발자가 아니라면 이 메서드를 왜 호출했는지 햇갈릴 수 있다. 그렇기에 빌더
패턴을 이용해 표현하였다.


### 정적 팩토리 메소드

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String email;

    private String nickname;

    private String name;

    private int age;

    private MemberResponse(String email, String nickname, String name, int age) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getEmail(), member.getNickname(),
                member.getName(), member.getAge());
    }
}
```

정적 팩토리 메소드는 객체 생성에 명확한 의미를 부여한다. 해당 메소드를 호출할 때마다 무의미한 객체 생성을 하는 것을 막을 수 있습니다. 객체 생성을 캡슐화 하여 내부 구현을 알 필요가 없습니다. 그렇기 때문에 좀 더 객체지향적인 설계에 가까워집니다. 

### 페이징

회원 조회 시 모든 데이터를 다 갖고 오게되면 엄청나게 많은 양의 데이터를 갖고 옵니다. 때문에 stream을 사용하더라도 ResponseDTO에 담아 데이터를 보내주기엔 어려움이 있기 때문에 페이징을 이용하여 데이터의 사이즈를 제한해두고 조회하는 방식을 사용했습니다.

MemberApiController.java

```java
@GetMapping
public ResponseEntity<List<MemberResponse>> findAll(
     @PageableDefault(size = 5, sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {

     return ResponseEntity.ok(memberService.findAll(pageable));
}
```

MemberService.java

```java
public List<MemberResponse> findAll(Pageable pageable) {
        Page<Member> findMemberAll = memberRepository.findAll(pageable);

        return findMemberAll.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
}
```

`PageableDefault`를 통해 Default page를 정해주고 id(PK) 기준으로 역순(DESC) 정렬하여 화면에 던져 주도록 설정했습니다. 쿼리 스트링을 이용하는 방식보다 좀 더 정확하게 Default 값을 개발자가 정의하여 의도대로 동작할 수 있다고 판단하여 이러하 선택을 했습니다.
