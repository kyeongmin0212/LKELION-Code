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
