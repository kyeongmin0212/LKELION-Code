package mission04;

/**
 * 디자인 역할의 아기사자.
 */
public class DesignLion extends Lion {

    public DesignLion(String name, String major, int generation, IntroducePolicy policy) {
        super(name, major, generation, policy);
    }

    @Override
    public Part part() {
        return Part.DESIGN;
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) Figma 로 와이어프레임을 그리고 UI 컴포넌트를 디자인한다.");
    }
}
