## TASK API
---

`GET`  **프로젝트 전체 조회** `/projects`

```json
[
  {
    "projectId": 1,
    "name": "2기 교육",
    "status": "TERMINATION",
    "adminId": "nikki",
    "members": [
      {
        "memberId": "gray",
        "nickname": "gray",
        "email": "gray@example.com"
      },
      ...
    ],
    "milestones": [
      {
        "milestoneId": 1,
        "name": "객체지향 익히기",
        "startAt": "2022-01-10",
        "endAt": "2022-01-17"
      },
      ...
    ],
    "tasks": [
      {
        "taskId": 1,
        "title": "2기 - 객체지향 프로그래밍",
        "content": "컨텐츠",
        "createdAt": "2022-01-10T00:00:00",
        "modifiedAt": "2022-01-10T00:00:00",
        "writerId": "nikki",
        "comments": [
          {
            "commentId": 1,
            "content": "spring 교육에 관한 댓글 내용",
            "writerId": "gray"
          },
          ...
        ]
      },
      
     ...
    ]
  },
  ...
]
```
