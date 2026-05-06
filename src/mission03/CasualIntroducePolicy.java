package mission03;

/**
 * 캐주얼(친근한 한 줄) 자기소개 포맷.
 *
 *  ▶ 같은 데이터지만 SNS 자기소개처럼 한 줄로 요약해 보여준다.
 *  ▶ StandardIntroducePolicy 를 그대로 두고 "추가"만으로 새 포맷을 도입할 수 있다는 점이
 *    정책 인터페이스를 분리한 핵심 효과다.
 */
public class CasualIntroducePolicy implements IntroducePolicy {

    @Override
    public void introduce(String name, String major, int generation, String role) {
        System.out.printf("안녕! 나는 %d기 %s 전공 %s, 역할은 %s 야 :)%n",
                generation, major, name, role);
    }
}
