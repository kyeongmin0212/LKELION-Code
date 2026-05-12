package mission04;

/**
 * 표준(공식) 자기소개 포맷.
 *
 *  ▶ 명단 출력처럼 "줄 단위로 정렬된" 형식이 필요할 때 쓴다.
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
