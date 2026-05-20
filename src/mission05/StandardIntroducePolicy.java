package mission05;

/**
 * 표준(공식) 자기소개 포맷 — 명단형으로 한 줄에 정리해 출력한다.
 *
 *  ▶ AppConfig 에서 이 구현체를 LionService 에 주입한다.
 *    포맷을 바꾸고 싶다면 새 구현체를 만들어 AppConfig 의 한 줄만 교체하면 된다.
 */
public class StandardIntroducePolicy implements IntroducePolicy {

    @Override
    public void introduce(String name, String major, int generation, String role) {
        System.out.println("- 이름: " + name
                + " / 전공: " + major
                + " / 기수: " + generation + "기"
                + " / 역할: " + role);
    }
}
