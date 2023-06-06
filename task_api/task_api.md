## TASK API
---
### 프로젝트 API
`GET`  `/projects/{projectId}` **프로젝트 세부 조회**
- response
```json
{
  "projectId": 1,
  "name": "2기 교육",
  "status": "TERMINATION",
  "members": [
    {
      "memberId": "gray",
      "role": "MEMBER"
    },
    {
      "memberId": "josh",
      "role": "MEMBER"
    },
    {
      "memberId": "nikki",
      "role": "ADMIN"
    },
    {
      "memberId": "oliver",
      "role": "MEMBER"
    },
    {
      "memberId": "scarf",
      "role": "MEMBER"
    }
  ],
  "milestones": [
    {
      "milestoneId": 1,
      "name": "객체지향 익히기",
      "startAt": "2022-01-10",
      "endAt": "2022-01-17"
    },
    {
      "milestoneId": 2,
      "name": "스프링부트 익히기",
      "startAt": "2022-01-20",
      "endAt": "2022-01-27"
    }
  ],
  "tags": [
    {
      "tagId": 1,
      "name": "객체지향"
    },
    {
      "tagId": 2,
      "name": "스프링 부트"
    }
  ],
  "tasks": [
    {
      "taskId": 1,
      "title": "2기 - 객체지향 프로그래밍",
      "writerId": "nikki"
    },
    {
      "taskId": 2,
      "title": "2기 - 스프링부트 입문",
      "writerId": "nikki"
    }
  ]
}

```


`GET`  `/projects/members/{memberId}` **멤버가 속한 프로젝트 이름조회**
- response
```json
{
  "memberId": "nicole",
  "projectNames": [
    {
      "name": "3기 교육"
    },
    {
      "name": "3기 과제"
    }
  ]
}

```

`GET`  `/projects/{projectId}/members` **프로젝트에 속한 멤버 조회**
- response
```json
[
  {
    "memberId": "gray",
    "role": "MEMBER"
  },
  {
    "memberId": "josh",
    "role": "MEMBER"
  },
  {
    "memberId": "nikki",
    "role": "ADMIN"
  },
  {
    "memberId": "oliver",
    "role": "MEMBER"
  },
  {
    "memberId": "scarf",
    "role": "MEMBER"
  }
]
```

`POST`  `/projects/{projectId}` **프로젝트 생성**
- request
```json
{
  "name": "새로운 프로젝트",
  "adminId" : "nikki"
}
```
- response
`201 created`
```json
{
  "adminId": "nikki",
  "projectId": 4,
  "name": "새로운 프로젝트",
  "status": "ACTIVATE"
}
```
`PUT`  `/projects/{projectId}` **프로젝트 수정**
- request
```json
{
  "name" : "수정된 프로젝트",
  "status" : "ACTIVATE"
}
```
- response
```json
{
  "projectId": 4,
  "name": "수정된 프로젝트",
  "status": "ACTIVATE"
}
```

`DELETE`  `/projects/{projectId}` **프로젝트 삭제**
- response, 삭제된 projectId 응답반환 
```json
{
  "projectId": 4
}
```


