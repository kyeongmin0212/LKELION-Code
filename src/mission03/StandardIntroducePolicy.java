package mission03;

/**
 * 표준(공식) 자기소개 포맷.
 *
 *  ▶ 명단 출력처럼 "딱 보기 좋게" 정렬된 형식이 필요할 때 쓴다.
 *  ▶ Mission02 의 introduce() 출력 모양을 확장해 "역할" 한 줄을 추가했다.
 */
public class StandardIntroducePolicy implements IntroducePolicy {

    @Override
    public void introduce(String name, String major, int generation, String role) {
        System.out.println("===== 아기사자 정보 =====");
        System.out.println("이름 : " + name);
        System.out.println("전공 : " + major);
        System.out.println("기수 : " + generation + "기");
        System.out.println("역할 : " + role);
    }
}
