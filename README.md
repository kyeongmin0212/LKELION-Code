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
