# Reservation System (공연 예약 서비스)

Spring Boot 기반 공연/좌석/예약 관리 시스템  
도메인 중심 패키지 구조 + Clean Layered Architecture 적용

---

## 1. 프로젝트 구조 (Domain 중심 구조)

```text
src
└─ main
   ├─ java
   │  └─ com.pjh.reservation
   │     ├─ ReservationApplication.java
   │     │
   │     ├─ common/                # 공통 설정 + 전역 정책
   │     │   ├─ config/            # Security, CORS, Swagger, JPA 등
   │     │   ├─ exception/         # 글로벌 예외 처리, ErrorResponse
   │     │   └─ util/              # 유틸리티성 클래스
   │     │
   │     ├─ user/                  # 사용자(User) 도메인
   │     │   ├─ web/               # Controller + Request/Response DTO
   │     │   ├─ application/       # UserService (Use Case)
   │     │   ├─ domain/            # User Entity, Domain Logic
   │     │   └─ infra/             # UserRepository(JPA)
   │     │
   │     ├─ performance/           # 공연 Performance 도메인
   │     │   ├─ web/
   │     │   ├─ application/
   │     │   ├─ domain/
   │     │   └─ infra/
   │     │
   │     └─ reservation/           # 예약 Reservation 도메인
   │         ├─ web/
   │         ├─ application/
   │         ├─ domain/
   │         └─ infra/
   │
   └─ resources
      ├─ application.properties     # 환경 설정
      └─ schema.sql                 # 테이블 자동 생성 SQL
```

---

## 2. 