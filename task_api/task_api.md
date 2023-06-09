## TASK API
---
- [프로젝트 API](#프로젝트-api)
- [작업 API](#-api)



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
{
  "projectId": 1,
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
  ]
}
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


###  API

`GET` `http://localhost:8081/tasks/{taskId}` **Task 상세 조회**
- response(task 에 대한 댓글, 마일스톤, 태그가 있을때)
```json
{
  "taskId": 1,
  "title": "2기 - 객체지향 프로그래밍",
  "content": "컨텐츠",
  "tags": [
    {
      "tagId": 1,
      "name": "객체지향"
    },
    {
      "tagId": 2,
      "name": "프로그래밍 패러다임"
    }
  ],
  "milestone": {
    "milestoneId": 1,
    "name": "객체지향 익히기",
    "startAt": "2022-01-10",
    "endAt": "2022-01-17"
  },
  "comments": [
    {
      "commentId": 1,
      "writerId": "gray",
      "content": "객체지향 교육에 관한 댓글 내용",
      "modifiedAt": "2022-01-11T11:30"
    },
    {
      "commentId": 2,
      "writerId": "oliver",
      "content": "객체지향 교육에 관한 댓글 내용",
      "modifiedAt": "2022-01-11T11:43"
    },
    {
      "commentId": 3,
      "writerId": "scarf",
      "content": "객체지향 교육에 관한 댓글 내용",
      "modifiedAt": "2022-01-12T12:22"
    },
    {
      "commentId": 4,
      "writerId": "josh",
      "content": "객체지향 교육에 관한 댓글 내용",
      "modifiedAt": "2022-01-12T15:32"
    }
  ]
}
```
- response(task 에 대한 댓글, 마일스톤, 태그가 없을때)
  - 마일스톤 X -> 반환하지 않음
  - 댓글, 태그 -> `[]` 빈리스트 반환
```json
{
  "taskId": 7,
  "title": "Spring boot/Microservice",
  "content": "content about Springboot/Microservice",
  "tags": [],
  "comments": []
}

```

`POST` `http://localhost:8081/tasks` **Task 생성**
- request
```json
{
  "title": "Spring boot/Microservice",
  "content": "content about Springboot/Microservice",
  "writerId" : "nikki",
  "projectId" : 2
}
```
- response
`201 created`
```json
{
  "taskId": 7,
  "projectId": 2
}
```
`PUT` `http://localhost:8081/tasks/{taskId}` **Task 수정**
- request
```json
{
  "title" : "update Spring boot",
  "content" :"update about content"
}
```
- response
```json
{
  "taskId": 3,
  "projectId": 2
}

```

`DELETE` `http://localhost:8081/tasks/{taskId}` **Task 삭제**
- response
```json
{
  "taskId": 1
}
```
