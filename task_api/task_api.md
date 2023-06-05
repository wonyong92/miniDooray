## TASK API
---
### 프로젝트 API

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
