## TASK API
---
### ERD
<img width="1016" alt="image" src="https://github.com/wonyong92/miniDooray/assets/81916321/2fba053a-786e-40d5-aa12-28a27a0b2402">

---
- [프로젝트 API](#프로젝트-api)
- [작업 API](#작업-api)
- [작업 태그 API](#작업-태그-api)
- [작업 마일스톤 API](#작업-마일스톤-api)
- [댓글 API](#댓글-api)
- [태그 API](#태그-api)
- [마일스톤 API](#마일스톤-api)
- [권한확인 API](#권한-확인-api)

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


### 작업 API

`GET` `/tasks/{taskId}` **Task 상세 조회**
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

`POST` `/tasks` **Task 생성**
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
`PUT` `/tasks/{taskId}` **Task 수정**
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

`DELETE` `/tasks/{taskId}` **Task 삭제**
- response
```json
{
  "taskId": 1
}
```

### 작업 태그 API

`POST` `/taskTags` **작업에 태그 생성**
- request

```json
{
  "taskId": 3,
  "tagIds" : [8, 9]
}
```
- response
`201 created`
```json
{
  "taskId": 3,
  "tagIds" : [8, 9]
}

```


`DELETE` `/taskTags` **작업에 태그 삭제**
- request
```json
{
  "taskId": 3,
  "tagIds" : [8, 9]
}
```
- response
```json
{
  "taskId": 3,
  "tagIds" : [8, 9]
}
```
### 작업 마일스톤 API

`POST` `/taskMilestones` **작업에 마일스톤 추가**
- request
```json
{
  "taskId" : 6,
  "milestoneId" : 6
}
```
- response
`201 created`
```json
{
  "taskId" : 6,
  "milestoneId" : 6
}
```


`DELETE` `/taskMilestones` **작업에 마일스톤 삭제**
- request
```json
{
  "taskId" : 6,
  "milestoneId" : 6
}
```
- response
```json
{
  "taskId" : 6,
  "milestoneId" : 6
}
```

### 댓글 API

`POST` `/comments` **댓글 생성**
- request
```json
{
  "taskId": 3,
  "writerId" : "steve",
  "content" : "db관련교육 댓글 작성 - steve"
}
```
- response
`201 created`
```json
{
  "commentId": 13,
  "taskId": 3
}
```

`GET` `/comments/{commentId}` **댓글 조회**
- response
```json
{
  "commentId": 10,
  "writerId": "steve",
  "content": "mvc관한 내용",
  "modifiedAt": "2023-05-12T11:11"
}

```
`PUT` `/comments/{commentId}` **댓글 수정**
- request
```json
{
  "content" : "수정된 댓글"
}
```
- response
```json
{
  "commentId": 11,
  "taskId": 5
}
```
`DELETE` `/comments/{commentId}` **댓글 삭제**
- response
```json
{
  "commentId": 12
}
```
### 태그 API

`POST` `/tags` **태그 생성**
- request
```json
{
  "name" : "태그1",
  "projectId" : 3
}
```
- response
`201 created`
```json
{
  "tagId": 11,
  "projectId": 3
}
```

`GET` `/tags/{tagId}` **태그 조회**
- response
```json
{
  "tagId": 11,
  "name": "태그1"
}
```
`PUT` `/tags/{tagId}` **태그 수정**
- request
```json
{
  "name" : "수정 태그"
}
```
- response
```json
{
  "tagId": 11,
  "projectId": 3
}
```
`DELETE` `/tags/{tagsId}` **태그 삭제**
- response
```json
{
  "tagId": 11,
  "projectId": 3
}
```
### 마일스톤 API
`POST` `/milestones` **마일스톤 생성**
- request
```json
{
  "projectId" : 3,
  "name": "신규 마일스톤",
  "startAt" : "2023-06-25",
  "endAt" : "2023-06-28"
}
```
- response
`201 created`
```json
{
  "projectId": 3,
  "milestoneId": 8
}
```

`GET` `/milestones/{milestoneId}` **마일스톤 조회**
- response
```json
{
  "milestoneId": 1,
  "name": "객제지향",
  "startAt": "2022-01-10",
  "endAt": "2022-01-17"
}
```
`PUT` `/milestones/{milestoneId}` **마일스톤 수정**
- request
```json
{
  "projectId" : 1,
  "name" : "객제지향 익히기 수정",
  "startAt" : "2022-01-10",
  "endAt" : "2022-01-17"
}
```
- response
```json
{
  "projectId": 1,
  "milestoneId": 1
}
```
`DELETE` `/milestones/{milestoneId}` **태그 삭제**
- response
```json
{
  "projectId": 3,
  "milestoneId": 9
}
```

### 권한 확인 API
`GET` `/tasks/auth/{taskId}` **작업 수정삭제 권한 확인**
- response
```json
{
  "taskId": 1,
  "projectId": 1
}
```


`GET` `/milestones/auth/{milestoneId}` **마일스톤 수정삭제 권한 확인**
- response
```json
{
  "milestoneId": 4,
  "projectId": 2
}
```

`GET` `/tags/auth/{tagId}` **태그 수정삭제 권한 확인**
- response
```json
{
  "tagId": 2,
  "projectId": 1
}
```

`GET` `/comments/auth/{commentId}` **댓글 수정삭제 권한 확인**

- response
```json
{
  "commentId": 10,
  "writerId": "steve",
  "projectId": 2
}
```
