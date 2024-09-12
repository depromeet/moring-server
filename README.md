
<div align=center>
  <img width="1100" alt="스크린샷 2024-08-07 오후 2 23 06" src="https://github.com/user-attachments/assets/e634746e-fc63-4e71-847a-942a5300272f">
</div>

## 🙌 Hello
- **모링 Server 팀**은 성공적인 모임 활동을 위한 서비스, **모링**의 서버를 개발 및 운영합니다.

## 🏛️ System Architecture
<div align=center>
  <img width="800" alt="moring-architecture drawio" src="https://github.com/user-attachments/assets/b42ad6b8-d452-4929-b07a-efb6f3495f27">
</div>

- **모링**의 서버는 **NCP** 환경에 기반하여 운영됩니다.
- 한 장비 내 **docker-compose** 기반으로 운영함으로써 비용 절감을 도모합니다.
    - 사용자 증가 시, **k8s** 기반 시스템으로 전환 가능하도록 구상 중에 있습니다.

## 🏗️ Application Architecture
- **DDD**(Domain Driven Design) 및 **Multi Module** 방식을 채택하였습니다.

### Domain Driven Design
- 애플리케이션 내 패키지는 **특정 도메인** 또는 **서브 도메인**을 기준으로 나뉩니다.
- 각 도메인 패키지는 **application**, **domain**, **infrastructure**, **presentation**의 네 가지 주요 레이어로 일관된 구조를 따릅니다.
```
├── meeting
│   ├── answer
│   │   ├── application
│   │   │   ├── MeetingAnswerRepository.java
// ...
│   │   ├── domain
│   │   │   └── MeetingAnswer.java
│   │   ├── infrastructure
│   │   │   ├── MeetingAnswerJpaRepository.java
// ...
│   │   └── presentation
│   │       ├── MeetingAnswerController.java
// ...
│   ├── comment
│   │   ├── application
// ...
```
- DDD의 가장 큰 **장점**은, 도메인 모델을 강조함으로써 소프트웨어가 **비즈니스 목표 및 프로세스와 밀접하게 일치**하도록 할 수 있다는 점입니다.
    - 이러한 장점으로 인해 **효율적이고 빠르며 정확한** 개발이 가능토록 합니다.

### Multi Module
- **Multi Module 아키텍처**는 프로젝트를 **여러 모듈로 분리**하여 각각의 모듈이 독립적으로 개발되고 관리될 수 있도록 합니다.
    - 이 방식은 **코드의 모듈성**과 **재사용성**을 높이며, **독립적인 배포**와 **테스트**가 가능하게 해줍니다.
```
├── auth
│   ├── src
│   └── build.gradle
├── meeting-api
│   ├── src
│   └── build.gradle
├── admin-api
│   ├── src
│   └── build.gradle
// ...
```

## ⏳ CI / CD
- TODO

## 🚨 Monitoring / Alert
- 다양한 지표와 로그를 효과적으로 수집하고 시각화하여 **서비스 가용성**과 **성능 최적화**에 기여합니다.
- 각 도구는 독립적이면서도 통합적으로 관리되어 **유연한 확장성**과 **효율적인 문제 진단**을 지원합니다.

1. **대시보드**
   -  **사용 기술**: `Prometheus`, `Grafana`, `Micrometer`
   - 시스템 및 애플리케이션 지표를 실시간으로 시각화하며, Slack 알림 설정 가능.

2. **애플리케이션 추적**
   - **사용 기술**: `Naver Pinpoint`
   - HTTP 요청 및 마이크로서비스 간 분산 추적.
   - 성능 병목을 빠르게 파악.

3. **로그**
   - **사용 기술**: `Loki Stack (Promtail + Loki + Grafana)`
   - 로그 데이터를 효율적으로 수집하고 필요 시 커스터마이징 가능.
   - Grafana에서 로그를 시각화하고, 검색 및 분석을 지원.

4. **에러 모니터링**
   - **사용 기술**: `Sentry`
   - 실시간 오류 모니터링 및 분석을 통해 빠른 대응 가능.


## ✅ Test
- TODO

## 🧑‍💻 Contribution
<div align=center>

| 김나현 | 권기준 | 이한음 |
|:---:|:---:|:---:|
| <a href="https://github.com/nahyeon99"> <img src="https://avatars.githubusercontent.com/u/69833665?v=4" width=100px alt="_"/> </a> | <a href="https://github.com/kkjsw17"> <img src="https://avatars.githubusercontent.com/u/39583312?v=4" width=100px alt="_"/> </a> | <a href="https://github.com/LeeHanEum"> <img src="https://avatars.githubusercontent.com/u/103233513?v=4" width=100px alt="_"/> </a> |
| **Server** (Leader) | **Server** | **Server** |

</div>