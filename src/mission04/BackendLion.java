package mission04;

/**
 * 백엔드 역할의 아기사자.
 *
 *  ▶ Mission03 과 거의 동일하지만, role() 대신 part() 를 오버라이딩한다.
 *    역할명은 Part enum 의 label 을 통해 자동으로 만들어진다.
 */
public class BackendLion extends Lion {

    public BackendLion(String name, String major, int generation, IntroducePolicy policy) {
        super(name, major, generation, policy);
    }

    @Override
    public Part part() {
        return Part.BACKEND;
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.");
    }
}
