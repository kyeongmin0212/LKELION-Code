package mission02;

/**
 * 아기사자 한 명을 표현하는 클래스.
 * - 세 필드는 의도적으로 서로 다른 접근 제어자(public / default / private)로 선언한다.
 * - 유효성 검증과 정보 출력은 Lion 자신이 책임진다 (캡슐화).
 * - 생성자에서는 콘솔 출력을 하지 않는다.
 */
public class Lion {

    public String name;       // public  : 어느 패키지에서든 접근 가능
    String major;             // default : 같은 패키지에서만 접근 가능
    private int generation;   // private : 같은 클래스 내부에서만 접근 가능

    /**
     * 단순 생성자 — Step 1에서 사용한다.
     * main이 먼저 유효성을 검증한 뒤 이 생성자를 호출하므로 여기서는 값만 초기화한다.
     */
    public Lion(String name, String major, int generation) {
        this.name = name;
        this.major = major;
        this.generation = generation;
    }

    /**
     * 정적 팩토리 메서드 — Step 2에서 사용한다.
     * Lion 클래스가 스스로 유효성을 검증한 뒤 객체를 생성한다.
     */
    public static Lion create(String name, String major, int generation) {
        validate(name, major, generation);
        return new Lion(name, major, generation);
    }

    /**
     * 유효성 검증.
     * - 이름 빈값 / 전공 빈값 / 기수 1 미만 을 모두 차단한다.
     */
    public static void validate(String name, String major, int generation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("전공은 빈 값일 수 없습니다.");
        }
        if (generation < 1) {
            throw new IllegalArgumentException("기수는 1 이상이어야 합니다.");
        }
    }

    /** 자기소개 출력 — 정보 관리도 Lion이 담당한다. */
    public void introduce() {
        System.out.println("===== 아기사자 정보 =====");
        System.out.println("이름 : " + name);
        System.out.println("전공 : " + major);
        System.out.println("기수 : " + generation + "기");
    }
}
