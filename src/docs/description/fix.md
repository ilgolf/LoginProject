# Fix 목록

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

4차 fix.

현재 @PathVariable을 통해 회원 id를 받아와 그 값을 토대로 회원 수정과 삭제를 하고 있었습니다. 하지만 이는 공격에 취약점이 될 수 있습니다. 예를 들어 상대방의 memberId가 노출이 쉽고 그 상태로 누군가 접속을 시도한다면 그대로 접속이 이루어져 쉽게 변경이나 삭제를 할 수 있을 것입니다. 그렇기 때문에 내부에 SecurityContext에 존재하는 회원 정보를 갖고오는게 맞다고 판단 @AuthenticationPrincipal로 인증된 사용자의 정보를 갖고 오게 됩니다.

이는 저희가 로그인 시 loadByUsername을 통하여 생성된 principal 객체로서 비교적 PathVariable에 비해 안전합니다.

ex) 회원 수정 Controller
```java
 // 회원 변경
    @PutMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal String email, @Valid @RequestBody MemberUpdateDTO updateDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 수정", updateDTO.getEmail());
        Member member = updateDTO.toEntity();
        memberService.updateMember(member, email);
        return ResponseEntity.ok().build();
    }

```

보시다 싶이 AuthencationPrincipal로 인증된 사용자의 email을 Context로부터 받아오게 끔 설계하여 외부로 부터 공개되지도 않는 이점이 있습니다.(Path에 아무런 정보도 남지 않았다.)

5차 fix.

PUT은 값 자체를 덮어 씌워 업데이트 시 문제가 발생할 수 있습니다.

ex. 기존 데이터name : "kim", age : 23

|메서드 |변경 데이터|               변경 후|
|------|----------|---------------------|
|PUT   |age : 20  |name : null, age : 20| 
|PATCH |age : 20  |name : kim, age : 20 |

데이터가 실수로 가지 않더라도 위 표 처럼 PATCH는 필요한 데이터만 변경하여 더욱 안전하기 때문에 PUT에서 발생한 문제를 해결하기 위해 PATCH를 사용했습니다.

6차 fix.

회원 조회 기능에서 회원이 없다면 빈 객체를 던지는 것이 아닌 예외를 발생시켜야 하는데 예외가 아닌 빈 객체를 던저주고 있었습니다.

문제의 원인은 다음 코드였습니다.

```java
@Transactional(readOnly = true)
public MemberResponse findByEmail(String email) {
  Member memmber = memberRepository.findByEmail(email).orElseGet(Member::new);
  
  return MemberResponse.from(member);
}
```

orElseGet으로 null 일 때에는 새로운 Member 객체를 만들어 빈 객체로 던져 주고 있었습니다. 빈 객체보다 예외를 발생시켜 무엇이 잘못되었는지 명확하게 전달해주어야 클라이언트와
개발자 모두에게 명확한 원인을 알 수 있게 해줍니다.

```java
@Transactional(readOnly = true)
public MemberResponse findByEmail(final String email) {
  Member member = memberRepository.findByEmail(email).orElseThrow(
       () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

  return MemberResponse.from(member);
}
```

다음 코드 처럼 orElseThrow() 메서드를 사용하여 명확하게 회원이 없다는 오류를 클라이언트와 개발자에게 전달하고 있습니다. 이렇게 설계 했을 때 문제 발생 시 명확하게 알 수 있습니다.
