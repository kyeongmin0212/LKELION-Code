package mission03;

/**
 * 디자인 역할의 아기사자.
 *
 *  ▶ "역할이 새로 추가될 때마다 main 의 if/instanceof 가 늘어나는" 구조 대신,
 *    이렇게 Lion 을 상속한 새 클래스 하나만 추가하면 끝이다 (OCP — 개방·폐쇄 원칙).
 */
public class DesignLion extends Lion {

    public DesignLion(String name, String major, int generation, IntroducePolicy policy) {
        super(name, major, generation, policy);
    }

    @Override
    public String role() {
        return "디자이너";
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) Figma 로 와이어프레임을 그리고 UI 컴포넌트를 디자인한다.");
    }
}
