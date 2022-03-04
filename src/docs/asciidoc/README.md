#Login Project API SPEC

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
  "email":"ssar@naver.com",
  "password":"123456789",
  "name":"ssar",
  "nickname":"ssss",
  "age":23
}
```

### Response Body

```JSON
{
  "email":"ssar@naver.com"
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
  "email":"ssar@naver.com",
  "nickname":"ssar",
  "name":"ssar",
  "age":23
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
  "email":"ilgolc@naver.com",
  "password":"abcdefghijk",
  "nickname":"kim23"
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
