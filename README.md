# LoginProject

## 프로젝트 시작 이유

아무생각 없이 api를 만들게 되고 기능 구현에만 목적을 둔 개발을 멈추고 싶었습니다. 그래서 본 프로젝트에서는 불필요한 부분을 걷어내고 효과 적인 코드를 짜는데 집중하고, RESTful 규약을 이해하고 RestFul한 설계를 경험하기 위해 시작했습니다.

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

## fix 목록

1차 fix. 

데이터를 저장하는 클래스인 Entity 클래스가 데이터를 전달하는 책임도 지고 있는 객체지향 적이지 못한 부분과 Entity를 반환하면서 Body에 불필요하거나 민감한 데이터가 넘어갈 수 있다고 판단
데이터를 전달하는 객체를 만들어서 민감한 정보를 제외하고 필요한 데이터를 DTO를 통해 전달

ex) 회원가입 전달 DTO 객체

```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    @Size(min = 5, max = 30)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 3, max = 10)
    private String name;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(min = 4, max = 20)
    private String nickName;

    @NotBlank(message = "나이를 입력해주세요")
    private Integer age;
}
```

2차 fix. 

서비스 객체가 변화가 자주 발생하는 객체인 DTO 객체를 의존하는 상황이 발생하고 유지보수 적으로 DTO가 변화게 되면 서비스 로직도 변화가 심해진다고 판단 (물론 이에 대한 의견은 여전히 분분하다고 생각합니다.) toEntity메서드를 이용하여 변화가 상대적으로 적은 Entity 객체로 반환하여 서비스 레이어에 내려보냈습니다. 

```java
public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .roleType(RoleType.USER)
                .nickname(nickName)
                .age(age)
                .build();
    }
```

3차 fix.

Exception을 좀 더 명확히하고 오류 페이지를 White label의 오류 페이지는 좋지 않습니다. 따로 Exception을 서버에서 처리해서 해당 예외에 대한 필요한 정보들만 화면에 보여주어야 하는게 더 효과적입니다. 

그리고 데이터를 전달할 때 Map과 Response객체를 고민했는데 이 때 저는 Map이 아닌 Response객체를 택했습니다. 이유는 Map에는 정확한 의미가 담겨 있지 않습니다. Map은 그냥 단순한 자료구조를 나타낼 뿐입니다. 그리고 예상할 수 없는 컴파일 에러가 발생합니다. Map은 저희가 정의한 것이 아닌 JDK에서 제공해주는 라이브러리기 때문입니다.

1. Global Exception

```java
@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ResponseError response = ResponseError.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final ResponseError response = ResponseError.of(ErrorCode.ACCESS_DENIED_ERROR);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.ACCESS_DENIED_ERROR.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseError> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ResponseError response = ResponseError.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

2. ResponseError

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseError {

    private String errorMessage;
    private int status;
    private String code;

    private ResponseError(final ErrorCode code) {
        this.errorMessage = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static ResponseError of(ErrorCode code) {
        return new ResponseError(code);
    }
}
```


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
