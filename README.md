# spring-redis-cache-play

# Spring Redis Cache 예제 프로젝트

## 프로젝트 개요
이 프로젝트는 Spring Boot와 Redis를 활용하여 캐싱 기능을 구현한 도서 관리 시스템입니다. 데이터 액세스 속도를 향상시키고 데이터베이스 부하를 줄이기 위해 Redis 캐시를 적용했습니다.

## 주요 기능
- 도서 정보 CRUD 작업
- ISBN 기준 도서 조회
- 저자별 도서 목록 조회
- 제목 키워드 검색
- 출판 연도별 필터링
- 가격 범위 기준 검색
- 저자별 특정 가격 이하 도서 검색

## 기술 스택
- Kotlin
- Spring Boot
- Spring Data JPA
- Spring Data Redis
- Gradle

## Redis 캐시 설정
- 기본 TTL: 10분
- 캐시별 다양한 TTL 설정
    - 모든 도서 목록: 5분
    - 개별 도서: 30분
    - 저자별 도서: 15분
    - 제목 검색 결과: 10분
    - 연도별 도서: 20분
    - 가격범위 검색 결과: 5분
    - 저자별 저가 도서: 15분

## API 엔드포인트
- `GET /api/books/{isbn}`: ISBN으로 도서 조회
- `GET /api/books`: 모든 도서 목록 조회
- `POST /api/books`: 새 도서 등록
- `PUT /api/books/{isbn}`: 도서 정보 수정
- `DELETE /api/books/{isbn}`: 도서 삭제
- `GET /api/books/author/{author}`: 저자별 도서 조회
- `GET /api/books/search?title={title}`: 제목으로 도서 검색
- `GET /api/books/year/{year}`: 출판 연도별 도서 조회
- `GET /api/books/price?min={min}&max={max}`: 가격 범위로 도서 검색
- `GET /api/books/author/{author}/price?price={price}`: 저자별 특정 가격 이하 도서 조회

## 실행 방법
1. docker-compose.yml 실행
2. 프로젝트 빌드: `./gradlew build`
3. 애플리케이션 실행: `./gradlew bootRun`

