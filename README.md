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
