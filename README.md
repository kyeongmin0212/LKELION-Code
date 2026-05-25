# LKELION-Code

멋쟁이사자처럼(LIKELION) PBL 미션 풀이 저장소.

- **작성자: 노경민**

## 미션 목록

| 미션 | 주제 | 위치 |
|---|---|---|
| Mission 00 | Hello World | [src/Mission00.java](src/Mission00.java) |
| Mission 01 | 아기사자 명단 관리 | [src/Mission01.java](src/Mission01.java) |
| Mission 02 | 객체지향 I — 클래스와 캡슐화 | [src/mission02/](src/mission02/) |
| Mission 03 | 객체지향 II — 상속 / 다형성 / 추상화 | [src/mission03/](src/mission03/) |
| Mission 04 | Java Collections & 설계 확장 | [src/mission04/](src/mission04/) |
| Mission 05 | 자바로 배우는 IoC / DI | [src/mission05/](src/mission05/) |
| Mission 06 | Spring Boot 전환 (IoC/DI → Spring 컨테이너) | [mission06/](mission06/) |

---

## Mission 03 — 객체지향 II : 상속 / 다형성 / 추상화

### 목표
- 추상 클래스 / 인터페이스를 정의하고 상속 구조를 설계한다.
- 역할별 하위 클래스를 만들어 다형성을 활용한다.
- `instanceof` 대신 오버라이딩으로 분기를 처리한다.

### 패키지 구조

```
src/mission03/
├── Lion.java                       (abstract) 공통 속성 + 추상 메서드
├── BackendLion.java                Lion 상속 — 백엔드 역할
├── FrontendLion.java               Lion 상속 — 프론트엔드 역할
├── DesignLion.java                 Lion 상속 — 디자인 역할
├── IntroducePolicy.java            (interface) 자기소개 출력 정책
├── StandardIntroducePolicy.java    공식 포맷 정책
├── CasualIntroducePolicy.java      캐주얼 포맷 정책
└── Mission03.java                  실행 진입점 (다형성 데모)
```

### 클래스 다이어그램 (텍스트)

```
                ┌──────────────────────────┐
                │   <<abstract>>  Lion     │
                │--------------------------│
                │ - name, major, generation│
                │ - introducePolicy        │◇──── IntroducePolicy <<interface>>
                │--------------------------│              ▲
                │ + introduce()            │              │
                │ + role()  (abstract)     │      ┌───────┴────────┐
                │ + work()  (abstract)     │      │                │
                └──────────▲───────────────┘  Standard          Casual
                           │                IntroducePolicy   IntroducePolicy
            ┌──────────────┼──────────────┐
            │              │              │
       BackendLion   FrontendLion    DesignLion
```

### 체크리스트 ↔ 코드 매핑

| 체크리스트 | 구현 위치 |
|---|---|
| 추상 클래스 또는 인터페이스가 정의되어 있는가 | [Lion.java](src/mission03/Lion.java) (`abstract class`), [IntroducePolicy.java](src/mission03/IntroducePolicy.java) (`interface`) |
| 공통 속성을 부모 클래스로 분리한 상속 구조가 있는가 | `Lion` 이 `name / major / generation` 을 보유, 자식들이 `extends Lion` |
| 역할별(백엔드/프론트엔드/디자인 등) 하위 클래스가 있는가 | [BackendLion.java](src/mission03/BackendLion.java), [FrontendLion.java](src/mission03/FrontendLion.java), [DesignLion.java](src/mission03/DesignLion.java) |
| 다형성을 활용하여 역할별 동작이 구현되었는가 | `Mission03.main` 의 `Lion[] lions` 루프에서 동일한 `lion.work()` 호출이 자식별로 다르게 실행 |
| `instanceof` 대신 오버라이딩으로 분기 처리했는가 | 전체 코드에 `instanceof` 0건. 분기 대신 `role()` / `work()` 오버라이딩으로 해결 |
| 정책 인터페이스를 통한 기능 분리가 있는가 | `IntroducePolicy` 인터페이스 + `Standard / Casual` 두 구현체로 출력 포맷을 외부 주입 |
| README.md 에 본인 이름 포함 | 본 문서 상단 "작성자: 노경민" |

### 실행 방법

```bash
# src 디렉터리에서
javac -encoding UTF-8 -d ../out mission03/*.java
java  -Dfile.encoding=UTF-8 -cp ../out mission03.Mission03
```

### 실행 결과 예시

```
===== 아기사자 정보 =====
이름 : 김백엔
전공 : 컴퓨터공학
기수 : 13기
역할 : 백엔드 개발자
김백엔은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.

안녕! 나는 13기 소프트웨어 전공 이프론, 역할은 프론트엔드 개발자 야 :)
이프론은(는) React 와 CSS 로 화면과 사용자 상호작용을 구현한다.

===== 아기사자 정보 =====
이름 : 박디자
전공 : 시각디자인
기수 : 13기
역할 : 디자이너
박디자은(는) Figma 로 와이어프레임을 그리고 UI 컴포넌트를 디자인한다.

안녕! 나는 13기 정보통신 전공 최서버, 역할은 백엔드 개발자 야 :)
최서버은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.

[검증 실패] 이름은 빈 값일 수 없습니다.
```

### 설계 포인트

1. **추상화** — `Lion` 은 "공통적으로 무엇이 있어야 하는가" 만 규정하고, 구체적인 행동은 자식에게 강제한다.
2. **상속** — `BackendLion / FrontendLion / DesignLion` 은 공통 속성과 검증을 다시 짤 필요가 없다.
3. **다형성** — `Lion[]` 한 배열에 자식 인스턴스를 같이 담고, 동일한 호출문(`lion.work()`)이 자식 타입별로 다르게 실행된다.
4. **`instanceof` 제거** — 역할별 분기는 자식 클래스의 오버라이딩이 흡수한다. 새 역할이 추가되어도 `Mission03.main` 은 손대지 않는다.
5. **정책 분리** — 자기소개 "출력 형식" 처럼 자주 바뀌는 부분은 `IntroducePolicy` 인터페이스로 빼서, 새 포맷이 필요하면 구현체만 추가하면 된다 (OCP).

---

## Mission 04 — Java Collections & 설계 확장

### 목표
- 고정 길이 배열 대신 `ArrayList`, `Map` 등 컬렉션으로 멤버를 관리한다.
- 제네릭(`List<Lion>`, `Map<Part, List<Lion>>`)을 올바르게 사용한다.
- 중복 등록 차단, 이름 검색, 파트별 필터링/그룹핑 같은 무결성·질의 로직을 저장소 한 곳에 모은다.

### 패키지 구조

```
src/mission04/
├── Part.java                       (enum) 파트 식별자 (BACKEND / FRONTEND / DESIGN)
├── Lion.java                       (abstract) 공통 속성 + 추상 메서드 + equals/hashCode
├── BackendLion.java                Lion 상속 — Part.BACKEND
├── FrontendLion.java               Lion 상속 — Part.FRONTEND
├── DesignLion.java                 Lion 상속 — Part.DESIGN
├── IntroducePolicy.java            (interface) 자기소개 출력 정책
├── StandardIntroducePolicy.java    표준 한 줄 포맷
├── LionRepository.java             ArrayList 저장 + Map 그룹핑 + 검색/필터 + 중복 차단
├── DuplicateLionException.java     중복 등록 시 던지는 도메인 예외
└── Mission04.java                  실행 진입점 (컬렉션 데모)
```

### 설계 다이어그램 (텍스트)

```
                Mission04.main
                      │
                      ▼
            ┌────────────────────────────┐
            │      LionRepository        │
            │----------------------------│
            │  List<Lion>            ◀── ArrayList (등록 순서 유지)
            │  Map<Part, List<Lion>> ◀── EnumMap (groupByPart)
            │----------------------------│
            │  register(Lion)            │── 중복이면 DuplicateLionException
            │  findAll() / size()        │
            │  findByName(String)        │── 부분 일치, 대소문자 무시
            │  filterByPart(Part)        │
            │  groupByPart()             │
            └────────────────────────────┘
                      │ holds
                      ▼
                ┌──────────────┐       Part (enum)
                │   Lion       │◇────  BACKEND / FRONTEND / DESIGN
                │  (abstract)  │
                └──────▲───────┘
            ┌─────────┼─────────┐
       BackendLion FrontendLion DesignLion
```

### 체크리스트 ↔ 코드 매핑

| 체크리스트 | 구현 위치 |
|---|---|
| 배열 대신 ArrayList 로 멤버를 관리하는가 | [LionRepository.java](src/mission04/LionRepository.java) 의 `private final List<Lion> lions = new ArrayList<>();` |
| Map 을 사용하여 파트별 멤버를 그룹핑하는가 | [LionRepository.groupByPart()](src/mission04/LionRepository.java) — `Map<Part, List<Lion>>` (`EnumMap` 구현) |
| 제네릭(List&lt;Lion&gt; 등)이 올바르게 사용되었는가 | 저장소 필드 / 모든 조회 메서드 반환 타입 / [Mission04.java](src/mission04/Mission04.java) 에서 캐스팅 0건 |
| 멤버 등록 시 중복 확인 로직이 있는가 | [LionRepository.register()](src/mission04/LionRepository.java) → `lions.contains(lion)` + [DuplicateLionException](src/mission04/DuplicateLionException.java). 동등성은 [Lion.equals/hashCode](src/mission04/Lion.java) (이름 + 기수) 가 정의 |
| 이름으로 멤버를 검색하는 기능이 있는가 | [LionRepository.findByName(String)](src/mission04/LionRepository.java) — 부분 일치, 대소문자 무시 |
| 파트별 멤버 필터링 기능이 있는가 | [LionRepository.filterByPart(Part)](src/mission04/LionRepository.java) — Part enum 키 기반 |
| GitHub README.md 에 본인 이름이 포함되었는가 | 본 문서 최상단 "작성자: 노경민" |

### 실행 방법

```bash
# src 디렉터리에서
javac -encoding UTF-8 -d ../out mission04/*.java
java  -Dfile.encoding=UTF-8 -cp ../out mission04.Mission04
```

### 실행 결과 예시

```
===== [1] 전체 멤버 (등록 순서 유지) — 총 5명 =====
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 최서버 / 전공: 정보통신 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 이프론 / 전공: 소프트웨어 / 기수: 13기 / 역할: 프론트엔드 개발자
- 이름: 정뷰어 / 전공: 디지털미디어 / 기수: 13기 / 역할: 프론트엔드 개발자
- 이름: 박디자 / 전공: 시각디자인 / 기수: 13기 / 역할: 디자이너

===== [2] 중복 등록 시도 =====
[차단됨] 이미 등록된 사자입니다: 김백엔 (13기)
[OK] 동명이인(12기)은 정상 등록 → 현재 인원: 6명

===== [3] 이름 검색: "김" 포함 =====
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 12기 / 역할: 백엔드 개발자
→ 2건 검색됨

===== [4] 파트 필터: BACKEND =====
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 최서버 / 전공: 정보통신 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 12기 / 역할: 백엔드 개발자

===== [5] 파트별 그룹핑 (Map) =====
[백엔드 파트] 3명
  · 김백엔 (컴퓨터공학, 13기)
  · 최서버 (정보통신, 13기)
  · 김백엔 (컴퓨터공학, 12기)
[프론트엔드 파트] 2명
  · 이프론 (소프트웨어, 13기)
  · 정뷰어 (디지털미디어, 13기)
[디자인 파트] 1명
  · 박디자 (시각디자인, 13기)
```

### 설계 포인트

1. **저장 책임의 이동** — Mission01 의 `Lion[5]` 같은 고정 배열을 main 이 직접 다루던 구조에서, 저장·검색·무결성은 `LionRepository` 가 전담하고 main 은 API 사용 예제만 남도록 했다.
2. **동등성을 도메인 객체가 정의** — 중복 판정의 의미("같은 사자란 무엇인가")를 저장소가 임의로 정하지 않고, `Lion.equals/hashCode` 에 박아둔다. 정체성 키는 `(이름, 기수)` — 동명이인이라도 기수가 다르면 다른 사자로 본다.
3. **enum 을 Map 키로** — `Part` 를 enum 으로 못박아 `Map<Part, List<Lion>>` 키로 사용한다. 문자열 키였다면 오타 한 번에 그룹이 깨지지만, enum 은 컴파일러가 막는다. 구현체는 enum 키 전용 최적화인 `EnumMap` 을 골라 선언 순서대로 안정적인 출력 순서를 얻는다.
4. **불변 뷰로 외부 노출** — 조회 메서드는 모두 `Collections.unmodifiableList(...)` 로 감싼 리스트를 반환한다. 호출자가 받은 리스트에 add/remove 해서 저장소 내부 상태가 깨지는 일을 컴파일 타임에 가깝게 차단한다.
5. **제네릭 = 캐스팅 0건** — 저장소 필드부터 반환 타입까지 모두 `<Lion>` 으로 매개변수화돼 있어, main 에서 `(Lion)` 같은 명시적 캐스팅이 단 한 줄도 등장하지 않는다.

---

## Mission 05 — 자바로 배우는 IoC / DI

### 목표
- `Repository` 인터페이스와 다수의 구현체를 만들어 DI 패턴을 이해한다.
- 생성자 주입으로 의존성을 외부에서 결정하는 구조를 구현한다.
- `AppConfig` 설정 클래스로 객체 조립을 분리한다.
- `main` 에서는 단 한 줄도 `new` 가 등장하지 않게 한다.

### 패키지 구조

```
src/mission05/
├── Part.java                       (enum) 파트 식별자 (BACKEND / FRONTEND / DESIGN)
├── Lion.java                       (abstract) 순수 도메인 객체 (정책 의존 제거)
├── BackendLion.java                Lion 상속 — Part.BACKEND
├── FrontendLion.java               Lion 상속 — Part.FRONTEND
├── DesignLion.java                 Lion 상속 — Part.DESIGN
├── IntroducePolicy.java            (interface) 자기소개 출력 정책
├── StandardIntroducePolicy.java    표준 한 줄 포맷
├── DuplicateLionException.java     중복 등록 시 던지는 도메인 예외
│
├── LionRepository.java             ▶ (interface) 저장소 추상 계약
├── MemoryLionRepository.java       ▶ 구현체 1 — ArrayList 기반 실제 저장소 (운영용)
├── MockLionRepository.java         ▶ 구현체 2 — 샘플 데이터 사전 적재 (데모/테스트용)
├── LionService.java                ▶ Repository + IntroducePolicy 를 생성자 주입으로 받는 서비스
├── AppConfig.java                  ▶ 의존성 그래프 조립 (Spring @Configuration 의 손코딩 버전)
└── Mission05.java                  ▶ 실행 진입점 — new 키워드 0건, AppConfig 만 호출
```

### 설계 다이어그램 (텍스트)

```
                        Mission05.main
                              │
                              │ 1) 호출
                              ▼
                       ┌──────────────┐
                       │  AppConfig   │  (정적 팩토리)
                       │--------------│
                       │ createLionService()      ──► MemoryLionRepository 주입
                       │ createDemoLionService()  ──► MockLionRepository   주입
                       └──────┬───────┘
                              │ 2) 조립해서 돌려줌
                              ▼
                       ┌──────────────────────────┐
                       │       LionService        │  (생성자 주입, 모두 final)
                       │--------------------------│
                       │  - LionRepository  repo  │◇── LionRepository <<interface>>
                       │  - IntroducePolicy policy│             ▲
                       │--------------------------│             │
                       │  enroll(name, ..., part) │  ┌──────────┴──────────┐
                       │  printRoster()           │  │                     │
                       │  printByPart(part)       │ Memory                Mock
                       │  printSearchByName(kw)   │ LionRepository      LionRepository
                       │  printWorkAssignments()  │ (운영용 / 빈 상태)   (샘플 3건 적재)
                       └──────────────────────────┘
```

### 체크리스트 ↔ 코드 매핑

| 체크리스트 | 구현 위치 |
|---|---|
| Repository 인터페이스가 정의되어 있는가 | [LionRepository.java](src/mission05/LionRepository.java) (`interface`) |
| 두 개 이상의 구현체가 있는가 | [MemoryLionRepository.java](src/mission05/MemoryLionRepository.java), [MockLionRepository.java](src/mission05/MockLionRepository.java) |
| Service 클래스가 Repository 에 의존하는 구조인가 | [LionService.java](src/mission05/LionService.java) — `private final LionRepository repository;` |
| 생성자를 통한 의존성 주입이 사용되었는가 | [LionService(LionRepository, IntroducePolicy)](src/mission05/LionService.java) — 모든 의존성 final, setter 없음 |
| AppConfig 등 설정 클래스에서 객체를 조립하는가 | [AppConfig.java](src/mission05/AppConfig.java) — `createLionService()` / `createDemoLionService()` 두 팩토리 |
| main 에서 직접 new 하지 않고 AppConfig 를 통해 객체를 생성하는가 | [Mission05.java](src/mission05/Mission05.java) — `new` 키워드 0건 (주석 제외) |
| GitHub README.md 에 본인 이름이 포함되었는가 | 본 문서 최상단 "작성자: 노경민" |

### 실행 방법

```bash
# src 디렉터리에서
javac -encoding UTF-8 -d ../out mission05/*.java
java  -Dfile.encoding=UTF-8 -cp ../out mission05.Mission05
```

### 실행 결과 예시

```
===== [A] 운영 시나리오: MemoryLionRepository 주입 =====
[전체 명단] 총 5명
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 최서버 / 전공: 정보통신 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 이프론 / 전공: 소프트웨어 / 기수: 13기 / 역할: 프론트엔드 개발자
- 이름: 정뷰어 / 전공: 디지털미디어 / 기수: 13기 / 역할: 프론트엔드 개발자
- 이름: 박디자 / 전공: 시각디자인 / 기수: 13기 / 역할: 디자이너

===== [A-1] 중복 등록 시도 (Repository 계약 검증) =====
[차단됨] 이미 등록된 사자입니다: 김백엔 (13기)

===== [A-2] 파트별 출력: BACKEND =====
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 최서버 / 전공: 정보통신 / 기수: 13기 / 역할: 백엔드 개발자

===== [A-3] 이름 검색: "김" 포함 =====
- 이름: 김백엔 / 전공: 컴퓨터공학 / 기수: 13기 / 역할: 백엔드 개발자
→ 1건 검색됨

===== [A-4] 각자 일하기 (다형성) =====
김백엔은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.
최서버은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.
이프론은(는) React 와 CSS 로 화면과 사용자 상호작용을 구현한다.
정뷰어은(는) React 와 CSS 로 화면과 사용자 상호작용을 구현한다.
박디자은(는) Figma 로 와이어프레임을 그리고 UI 컴포넌트를 디자인한다.

===== [B] 데모 시나리오: MockLionRepository 주입 (Service 코드 변경 없음) =====
[샘플 명단] 총 3명 (Mock 저장소가 생성자에서 미리 깔아둠)
- 이름: 샘플백 / 전공: 샘플학과 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 샘플프 / 전공: 샘플학과 / 기수: 13기 / 역할: 프론트엔드 개발자
- 이름: 샘플디 / 전공: 샘플학과 / 기수: 13기 / 역할: 디자이너

[추가 등록 후] 총 4명
- 이름: 샘플백 / 전공: 샘플학과 / 기수: 13기 / 역할: 백엔드 개발자
- 이름: 추가테스터 / 전공: 테스트학과 / 기수: 13기 / 역할: 백엔드 개발자
```

### 설계 포인트

1. **IoC (제어의 역전)** — `LionService` 는 자기가 쓸 협력자(`LionRepository`, `IntroducePolicy`)를 직접 만들지 않는다. "어느 구현체를 쓸지" 의 결정권은 `AppConfig` 한 곳으로 이전됐다. Service 코드는 단 한 글자도 바꾸지 않고도 `MemoryLionRepository` ↔ `MockLionRepository` 를 교체할 수 있다 (`AppConfig.createLionService()` vs `createDemoLionService()`).
2. **생성자 주입 + 불변 의존성** — `LionService` 의 두 의존 필드는 모두 `final` 이며 생성자에서만 주입된다. setter 가 없으므로 객체 수명 동안 의존성이 바뀌지 않고, 누락된 의존성은 객체 생성 즉시 예외로 드러난다 (지연 발견 X).
3. **인터페이스 계약의 강제** — `LionRepository` 는 "save 는 중복이면 `DuplicateLionException` 을 던진다", "조회는 절대 null 을 반환하지 않는다" 같은 의미 규칙을 자바독으로 명시한다. 구현체(Memory / Mock) 가 무엇이든 호출 측은 한 가지 예외/리턴 규약만 알면 된다.
4. **main 에 new 키워드 0건** — Mission05.main 은 `AppConfig.createLionService()` 만 호출하면 의존성 그래프가 통째로 조립된 `LionService` 를 받는다. Lion 인스턴스조차 `service.enroll(name, major, generation, Part.X)` 가 내부적으로 만들어주므로, main 에서 `BackendLion / FrontendLion / DesignLion` 같은 구체 클래스 이름이 한 번도 등장하지 않는다.
5. **두 시나리오의 등가성** — `[A]` 운영 시나리오와 `[B]` 데모 시나리오는 호출하는 Service 메서드(`printRoster`, `printByPart`, `enroll` …)가 완전히 동일하다. 다른 결과를 내는 유일한 원인은 AppConfig 가 주입한 Repository 구현체뿐이다 — DI 가 약속하는 "구성(construction)과 사용(use)의 분리" 가 실증되는 지점이다.

---

## Mission 06 — Spring Boot 전환 (IoC/DI → Spring 컨테이너)

### 목표
- Spring Initializr 로 Spring Boot 프로젝트를 생성하고, Mission05 의 순수 자바 IoC/DI 코드를 그대로 이전한다.
- 손코딩이었던 `AppConfig` 의 역할을 Spring 컨테이너에 넘긴다.
- `@Service`, `@Repository` 어노테이션과 **생성자 주입** 을 적용한다.
- `@Configuration + @Bean` 의 수동 등록 방식과 컴포넌트 스캔(자동 등록) 방식을 한 프로젝트 안에서 함께 사용한다.
- 기본 웹 엔드포인트 `GET /hello` 를 노출해 부트가 정상 기동했는지 확인한다.

### 프로젝트 위치 / 빌드 정보

| 항목 | 값 |
|---|---|
| 위치 | [mission06/](mission06/) |
| 빌드 도구 | Maven (`mission06/pom.xml`) |
| Spring Boot | 3.3.5 |
| Java | 17 |
| 그룹 / 아티팩트 | `com.likelion` / `mission06` |
| 베이스 패키지 | `com.likelion.mission06` |

### 패키지 구조

```
mission06/
├── pom.xml                              ▶ Spring Boot 3.3.5 + spring-boot-starter-web
└── src/main/
    ├── resources/
    │   └── application.properties        ▶ 포트 8080
    └── java/com/likelion/mission06/
        ├── Mission06Application.java     ▶ @SpringBootApplication + CommandLineRunner 데모
        ├── config/
        │   └── AppConfig.java            ▶ @Configuration + @Bean (IntroducePolicy 수동 등록)
        ├── controller/
        │   └── HelloController.java      ▶ @RestController, GET /hello
        ├── service/
        │   └── LionService.java          ▶ @Service + 생성자 주입
        ├── repository/
        │   ├── LionRepository.java       ▶ (interface)
        │   ├── MemoryLionRepository.java ▶ @Repository + @Primary (운영용 기본 구현체)
        │   └── MockLionRepository.java   ▶ @Repository("mockLionRepository") (데모용 구현체)
        ├── policy/
        │   ├── IntroducePolicy.java      ▶ (interface)
        │   └── StandardIntroducePolicy.java ▶ AppConfig 가 @Bean 으로 수동 등록
        ├── domain/
        │   ├── Lion.java                 ▶ (abstract) 도메인 객체 — 빈 아님
        │   ├── BackendLion / FrontendLion / DesignLion
        │   └── Part.java                 ▶ (enum)
        └── exception/
            └── DuplicateLionException.java
```

### 의존성 그래프 (Spring 컨테이너가 조립)

```
       @RestController HelloController
                │  ctor inject
                ▼
            @Service LionService
                │  ctor inject
       ┌────────┴─────────┐
       ▼                  ▼
 LionRepository       IntroducePolicy
       ▲                  ▲
       │ @Primary         │ @Bean (AppConfig)
       │                  │
@Repository MemoryLionRepository    StandardIntroducePolicy
@Repository("mockLionRepository") MockLionRepository
```

- `LionService` 는 생성자가 한 개이므로 Spring 이 `@Autowired` 없이도 자동 생성자 주입을 적용한다.
- `LionRepository` 구현체가 두 개이지만 `MemoryLionRepository` 에 `@Primary` 가 붙어 있어 충돌 없이 기본 주입된다.
- `IntroducePolicy` 는 클래스에 `@Component` 가 없고, `AppConfig.introducePolicy()` 의 `@Bean` 메서드로만 등록된다 → **수동 등록 증거**.

### 체크리스트 ↔ 코드 매핑

| 체크리스트 | 구현 위치 |
|---|---|
| Spring Boot 프로젝트(Spring Initializr)가 생성되었는가 | [mission06/pom.xml](mission06/pom.xml) — `spring-boot-starter-parent 3.3.5`, `spring-boot-starter-web` |
| 5주차 코드가 Spring Boot 구조로 이전되었는가 | `src/mission05/*` → [mission06/src/main/java/com/likelion/mission06/](mission06/src/main/java/com/likelion/mission06/) 의 `domain / repository / service / policy / exception` 패키지 |
| `@Service`, `@Repository` 어노테이션이 사용되었는가 | [LionService.java](mission06/src/main/java/com/likelion/mission06/service/LionService.java) (`@Service`), [MemoryLionRepository.java](mission06/src/main/java/com/likelion/mission06/repository/MemoryLionRepository.java) / [MockLionRepository.java](mission06/src/main/java/com/likelion/mission06/repository/MockLionRepository.java) (`@Repository`) |
| 생성자 주입이 적용되어 있는가 | `LionService(LionRepository, IntroducePolicy)`, `HelloController(LionService)` — 모두 단일 생성자 + `final` 필드, setter 없음 |
| `GET /hello` API 가 정상 동작하는가 | [HelloController.java](mission06/src/main/java/com/likelion/mission06/controller/HelloController.java) — `@GetMapping("/hello")` |
| `@Configuration + @Bean` 수동 등록 또는 자동 등록 방식을 사용했는가 | **둘 다 사용**: [AppConfig.java](mission06/src/main/java/com/likelion/mission06/config/AppConfig.java) (`@Configuration + @Bean` 수동) + `@Service / @Repository / @RestController` (컴포넌트 스캔 자동) |
| GitHub README.md 에 본인 이름이 포함되었는가 | 본 문서 최상단 "작성자: 노경민" |

### 실행 방법

프로젝트에 **Maven Wrapper** 가 포함되어 있어 별도의 Maven 설치 없이 바로 빌드/실행할 수 있다 (JDK 17 만 필요).

```bash
# 1) mission06 디렉터리로 이동
cd mission06

# 2) Spring Boot 실행 (Maven Wrapper)
#    Windows PowerShell / cmd:
.\mvnw.cmd spring-boot:run
#    macOS / Linux:
./mvnw spring-boot:run

# 3) 다른 터미널에서 엔드포인트 확인
curl http://localhost:8080/hello
# → Hello, Spring Boot! (Mission06 - LKELION 작성자: 노경민)

curl http://localhost:8080/hello/lions
# → CommandLineRunner 가 미리 등록한 사자 4명의 toString 목록
```

> 8080 포트가 점유돼 있으면 `SERVER_PORT=8089 ./mvnw spring-boot:run` (Unix) 또는
> `$env:SERVER_PORT=8089; .\mvnw.cmd spring-boot:run` (PowerShell) 처럼 다른 포트로 띄울 수 있다.

### 동작 검증 (이 저장소에서 직접 확인됨)

JDK 17 + Maven Wrapper 환경에서 다음을 확인했다:

| 단계 | 명령 | 결과 |
|---|---|---|
| 컴파일 | `./mvnw clean compile` | `BUILD SUCCESS` (15개 소스 파일) |
| 부팅 | `./mvnw spring-boot:run` | `Started Mission06Application in 1.5s`, Tomcat 8089 |
| 빈 주입 | CommandLineRunner 로그 | `LionService` 자동 주입 → 사자 4명 등록 후 `printRoster` 정상 출력 |
| `GET /hello` | `curl` | HTTP 200 + `Hello, Spring Boot! (Mission06 - LKELION 작성자: 노경민)` |
| `GET /hello/lions` | `curl` | HTTP 200 + 등록된 4명의 JSON 배열 |

### 동작 시연

- 기동 직후 `CommandLineRunner` (= `Mission06Application#demoRunner`) 가 한 번 실행되어, Spring 이 주입한 `LionService` 로 사자 4명을 등록·출력한다.
- 그 후 `GET /hello` 는 단순 인사 문자열을, `GET /hello/lions` 는 위에서 등록된 사자 명단을 JSON 배열로 반환한다 — Spring DI 와 웹 계층이 둘 다 살아있다는 증거.

### Mission05 와의 비교 (한 줄 요약)

| 관심사 | Mission05 (순수 Java) | Mission06 (Spring Boot) |
|---|---|---|
| 빈 조립자 | `AppConfig` 정적 팩토리 | Spring `ApplicationContext` |
| 의존성 표시 | `AppConfig` 안에서 `new LionService(...)` | `@Service` + 단일 생성자 → 자동 생성자 주입 |
| 구현체 선택 | `createLionService()` vs `createDemoLionService()` 두 팩토리 | `@Primary` (기본) + `@Qualifier("mockLionRepository")` (대안) |
| 정책 교체 | `AppConfig` 메서드 한 줄 수정 | `AppConfig#introducePolicy()` `@Bean` 메서드 한 줄 수정 |
| 진입점 | `public static void main` — `AppConfig.createLionService()` 호출 | `SpringApplication.run(...)` — 컨테이너가 그래프를 통째로 조립 |

### 설계 포인트

1. **자동 + 수동 등록의 의도적 공존** — `@Service / @Repository / @RestController` 로 컴포넌트 스캔의 편의를 보여주고, `@Configuration + @Bean` 으로 "갈아끼울 가능성이 높은 협력자(=정책)" 를 명시적으로 노출하는 두 가지 등록 패턴을 한 프로젝트에서 함께 시연한다.
2. **생성자 주입 + `final` 필드 고수** — Mission05 의 불변 의존성 원칙을 그대로 유지한다. 필드 주입이 아니라 생성자 시그니처 한 줄로 "이 클래스가 무엇을 필요로 하는가" 가 드러난다. 단일 생성자라 `@Autowired` 도 생략된다.
3. **여러 구현체 + `@Primary`** — 같은 타입의 빈이 둘 이상이면 Spring 은 충돌을 알리며 기동을 중단시킨다. `MemoryLionRepository` 에 `@Primary` 를 박아 기본 선택을 명시하고, `MockLionRepository` 는 이름(`"mockLionRepository"`) 으로만 접근 가능하게 했다 → Mission05 의 두 팩토리(`createLionService` / `createDemoLionService`) 와 동일한 의도를 Spring 어휘로 옮긴 것.
4. **컨트롤러도 DI 의 일부** — `HelloController` 는 `LionService` 를 생성자로 주입받는다. 웹 계층까지 같은 IoC 컨테이너 안에 들어와 있다는 점을 보여주는 가장 단순한 증거이다.
5. **`CommandLineRunner` 로 부트 시 자동 시연** — Mission05 의 `main` 시연 흐름이 Spring 환경에서도 동일하게 동작함을 부팅 직후 한 번 출력해 확인한다. 별도 클라이언트 호출 없이도 콘솔에서 즉시 검증 가능.
