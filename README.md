# 게시판 프로젝트

이 프로젝트는 Spring Boot를 기반으로 한 게시판 관리 서비스입니다. REST API, 데이터베이스 관리, Actuator 모니터링 등의 기능을 원활하게 개발하고 통합하기 위해 다양한 도구와 라이브러리를 사용합니다.

## 요구 사항

- **Java**: 17
- **Spring Boot**: 3.3.1
- **Maven Central**: 의존성 관리를 위한 주요 리포지토리

## 프로젝트 설정

로컬에서 프로젝트를 설정하려면 다음 단계를 따르세요:

1. 저장소를 클론합니다:

   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   cd your-repo-name
   ```

2. Java 17이 설치되어 있는지 확인합니다.

3. Gradle을 사용해 프로젝트를 빌드합니다:

   ```bash
   ./gradlew build
   ```

## 의존성

이 프로젝트는 다음과 같은 주요 의존성을 사용합니다:

- **Spring Boot Starter Actuator**: 애플리케이션 모니터링 및 관리.
- **Spring Boot Starter Web**: RESTful API 구축.
- **Spring Boot Starter Data JPA**: JPA를 사용한 데이터베이스 상호작용.
- **Spring Boot Starter Data REST**: JPA 리포지토리를 REST 엔드포인트로 노출.
- **Spring Data REST HAL Explorer**: HAL 브라우저를 통한 API 테스트.

### 런타임 의존성:

- **H2 Database**: 로컬 개발용 인메모리 데이터베이스.
- **MySQL Connector**: 프로덕션 환경에서 MySQL 데이터베이스 연결.

### QueryDSL 통합

이 프로젝트는 **QueryDSL**을 사용해 타입 안전 쿼리를 작성합니다. `build.gradle` 파일은 QueryDSL JPA 및 코어 라이브러리와 필요한 어노테이션 프로세서를 포함하도록 설정되어 있습니다.

- **QueryDSL JPA**: JPA 엔티티에서 타입 안전 쿼리 작성.
- **QueryDSL Core** 및 **QueryDSL Collections**: 코어 쿼리 기능.

QueryDSL의 버전은 Spring의 의존성 관리 플러그인을 통해 관리되므로 수동으로 명시할 필요는 없습니다.

## Lombok

이 프로젝트는 코드를 간결하게 만들기 위해 **Lombok**을 사용합니다. IDE에서 Lombok 지원을 활성화해야 하며, Lombok 어노테이션은 컴파일 시 처리됩니다.

## 개발 도구

- **Spring Boot DevTools**: 개발 중 핫 리로드 기능 제공.
- **JUnit Platform**: 테스트 실행 도구.
