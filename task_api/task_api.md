## TASK API
---
### 프로젝트 API
`GET`  `/projects/{projectId}` **프로젝트 세부 조회**

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
