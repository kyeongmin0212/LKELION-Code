package mission03;

/**
 * 프론트엔드 역할의 아기사자.
 *
 *  ▶ BackendLion 과 똑같은 부모(Lion)를 상속하지만,
 *    role() 과 work() 를 다르게 오버라이딩하기 때문에 동작이 완전히 달라진다.
 *    이게 "다형성 — 같은 호출, 다른 실행" 의 가장 단순한 예시다.
 */
public class FrontendLion extends Lion {

    public FrontendLion(String name, String major, int generation, IntroducePolicy policy) {
        super(name, major, generation, policy);
    }

    @Override
    public String role() {
        return "프론트엔드 개발자";
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) React 와 CSS 로 화면과 사용자 상호작용을 구현한다.");
    }
}
