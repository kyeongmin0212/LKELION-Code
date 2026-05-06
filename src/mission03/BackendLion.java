package mission03;

/**
 * 백엔드 역할의 아기사자.
 *
 *  ▶ Lion 의 추상 메서드 두 개(role, work)를 오버라이딩으로 채워 넣는다.
 *  ▶ 호출하는 쪽에서는 Lion 타입으로만 들고 다녀도, 실제 동작은 이 클래스 것이 실행된다.
 */
public class BackendLion extends Lion {

    public BackendLion(String name, String major, int generation, IntroducePolicy policy) {
        super(name, major, generation, policy);
    }

    @Override
    public String role() {
        return "백엔드 개발자";
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.");
    }
}
