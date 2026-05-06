package mission03;

/**
 * Mission03 — 객체지향 II : 상속 / 다형성 / 추상화
 *
 *  ▶ 핵심 데모: 같은 Lion 타입의 배열에 백엔드/프론트엔드/디자인 사자를 같이 담고,
 *               for 문 한 번으로 lion.introduce() / lion.work() 만 호출한다.
 *               instanceof 가 한 번도 안 나오지만, 각자의 역할에 맞는 동작이 자동으로 실행된다.
 *
 *  ▶ 이 main 이 "instanceof 없이도 분기처럼 동작하는" 이유:
 *      1) Lion 이 abstract 로 role() / work() 를 강제했고
 *      2) 각 자식이 자기 방식대로 오버라이딩했기 때문.
 *     → JVM 이 실제 객체 타입을 보고 알아서 메서드를 골라준다 (동적 디스패치).
 *
 *  ▶ 출력 포맷도 두 가지(Standard / Casual) 정책을 섞어서 넣어,
 *    "정책 인터페이스로 기능을 분리"한 효과까지 한 화면에서 보이도록 했다.
 */
public class Mission03 {

    public static void main(String[] args) {

        // 1) 정책 객체 — 자기소개 출력 형식을 결정한다.
        IntroducePolicy standard = new StandardIntroducePolicy();
        IntroducePolicy casual   = new CasualIntroducePolicy();

        // 2) 같은 Lion 타입 배열에 서로 다른 역할의 사자를 담는다.
        //    → 배열이 가지는 정적 타입은 Lion[] 이지만,
        //      안에 들어가는 실제 타입은 백엔드/프론트엔드/디자인으로 모두 다르다.
        Lion[] lions = {
                new BackendLion ("김백엔", "컴퓨터공학",  13, standard),
                new FrontendLion("이프론", "소프트웨어",  13, casual),
                new DesignLion  ("박디자", "시각디자인", 13, standard),
                new BackendLion ("최서버", "정보통신",   13, casual)
        };

        // 3) 분기 없이 동일한 호출만 반복한다.
        //    if (lion instanceof BackendLion) { ... } 같은 코드는 단 한 줄도 없다.
        for (Lion lion : lions) {
            lion.introduce();   // 정책에 따라 출력 포맷이 달라진다
            lion.work();        // 자식 클래스 오버라이딩에 따라 동작이 달라진다
            System.out.println();
        }

        // 4) 잘못된 입력은 Lion 자신이 거른다 (Mission02 캡슐화 유지).
        try {
            new BackendLion("", "컴퓨터공학", 13, standard);
        } catch (IllegalArgumentException e) {
            System.out.println("[검증 실패] " + e.getMessage());
        }
    }
}
