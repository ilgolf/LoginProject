# LoginProject

## 프로젝트 시작 이유 

무작정 Security로 로그인을 구현하다 정작 Session관리나 스프링에서 순수 코드로 작성 시 어떤 로직으로 작성될까 하는 호기심으로 시작했습니다.
또한 Session관리를 했을 때 Security가 왜 필요한지에 대한 의문이 들어 직접 경험해 보고싶어 시작한 프로젝트 입니다.

## 로그인 API

```java
@PostMapping("/login")
    public String login(@RequestBody Member member, HttpServletRequest request,
                        HttpServletResponse response, RedirectAttributes attributes) {
        HttpSession session = request.getSession();
        member = userService.login(member);

        if (member == null) {
            throw new IllegalStateException("아직 회원 가입 안하셨습니다.");
        }

        return "redirect:/";
    }
```

### Session의 동작 순서

1. 클라이언트가 서버에 요청을 보냄
2. 서버에서는 session id 쿠키 값이 없는 것을 확인하고 새로 발급해서 응답
3. 이후 클라이언트는 전달 받은 session id 값을 매 요청마다 헤더 쿠키에 넣어서 요청
4. 서버는 session id를 확인하여 사용자를 식별
5. 클라이언트가 로그인을 요청하면 서버는 session을 로그인한 사용자 정보로 갱신하고 새로운 session id를 발급하여 응답
6. 이후 클라이언트는 로그인 사용자의 session id 쿠키를 요청과 함께 전달하고 서버에서 로그인된 사용자로 식별 가능
7. 클라이언트 종료 (브라우저 종료)시 session id 제거, 서버에서도 세션 제거

### Session의 특징

- Session id는 브라우저 단위로 저장되고 브라우저 종료 시 소멸됩니다.
- 로그인한 사용자에 대해서만 Session을 생성하는 것이 아닙니다. 따라서 로그아웃하면 새로운 사용자로
  인식해서 새로운 Session이 생성됩니다.
- 사용자가 로그인 했는지, 닉네임 등의 사용자가 요청할 때 마다 필요한 정보들을 Session에 담아두면 사용자
  DB에 접근할 필요가 없어 효율적입니다.
  
  
  ref - https://cjh5414.github.io/cookie-and-session/
