# LoginProject

## 프로젝트 시작 이유

아무생각 없이 api를 만들게 되고 기능 구현에만 목적을 둔 개발을 멈추고 싶었습니다. 그래서 
본 프로젝트에서는 불필요한 부분을 걷어내고 효과 적인 코드를 짜는데 집중하고 ,생각 없는 
RestAPI 규약을 이해하고 RestFul한 설계를 경험하기 위해 시작했습니다.

## RestAPI 설계

- Create
  - path : /members/join
  - RequestMethod : Post
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error


- Read
  - path : /members/{id}
  - RequestMethod : Get
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error


- Update
  - path : /members/{id}
  - RequestMethod : Put
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error


- Delete
  - path : /members/{id}
  - RequestMethod : Delete
  - Success status : 200 OK
  - fail status : 4xx error, 5xx error

## 회원가입 API

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

