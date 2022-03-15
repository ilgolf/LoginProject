# Login Project API SPEC

FindAll과 SearchMember는 데이터를 일일 히 추가하기 복잡하여 다른 예시를 이용하였으니
참고 바랍니다.

-----------------------------------------

## Create Member

### Request

```HTTP
POST /members/join HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 90
Host: localhost:8080
```

### Response

```HTTP
HTTP/1.1 201 Created
Location: /members/findByEmail
Content-Type: text/plain;charset=UTF-8
Content-Length: 14
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
```

### Request Body

```JSON
{
  "email": "ssar@naver.com",
  "password": "123456789",
  "name": "ssar",
  "nickname": "ssss",
  "age": 23
}
```

### Response Body

```JSON
{
  "email": "ssar@naver.com"
}
```

## FindByEmail

### Request

```HTTP
GET /members/findByEmail HTTP/1.1
Accept: application/json
Host: localhost:8080
```

### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 67
```

### Response Body

```JSON
{
  "email": "ssar@naver.com",
  "nickname": "ssar",
  "name": "ssar",
  "age": 23
}
```

## Update

### Request

```HTTP
PATCH /members HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 72
Host: localhost:8080
```

### Response

```HTTP
HTTP/1.1 200 OK
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
```

### Request Body

```JSON
{
  "email": "ilgolc@naver.com",
  "password": "abcdefghijk",
  "nickname": "kim23"
}
```

## Delete

### Request

```HTTP
DELETE /members HTTP/1.1
Host: localhost:8080
```

### Response

```HTTP
HTTP/1.1 204 No Content
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
```

### Search

### Request

```HTTP
GET /members/{name} HTTP/1.1
Accept: application/json
Host: localhost:8080
```

### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 71

[{"email":"ilgolc@naver.com","nickname":"ssar","name":"kim3","age":23}]
```

### Response Body

```JSON
[
  {
    "email": "member50@naver.com",
    "nickname": "fasdf50",
    "name": "kim49",
    "age": 59
  },
  {
    "email": "member49@naver.com",
    "nickname": "fasdf49",
    "name": "kim48",
    "age": 58
  },
  {
    "email": "member48@naver.com",
    "nickname": "fasdf48",
    "name": "kim47",
    "age": 57
  },
  {
    "email": "member47@naver.com",
    "nickname": "fasdf47",
    "name": "kim46",
    "age": 56
  },
  {
    "email": "member46@naver.com",
    "nickname": "fasdf46",
    "name": "kim45",
    "age": 55
  }
]
```

### FindAll

### Request

```HTTP
GET /members HTTP/1.1
Accept: application/json
Host: localhost:8080
```

### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 71

[{"email":"ilgolc@naver.com","nickname":"ssar","name":"kim3","age":23}]
```

### Response Body

```JSON
[
  {
    "email": "member50@naver.com",
    "nickname": "fasdf50",
    "name": "kim49",
    "age": 59
  },
  {
    "email": "member49@naver.com",
    "nickname": "fasdf49",
    "name": "kim48",
    "age": 58
  },
  {
    "email": "member48@naver.com",
    "nickname": "fasdf48",
    "name": "kim47",
    "age": 57
  },
  {
    "email": "member47@naver.com",
    "nickname": "fasdf47",
    "name": "kim46",
    "age": 56
  },
  {
    "email": "member46@naver.com",
    "nickname": "fasdf46",
    "name": "kim45",
    "age": 55
  }
]
```