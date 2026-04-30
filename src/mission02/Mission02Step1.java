package mission02;

import java.util.Scanner;

/**
 * Step 1 — main이 직접 유효성 검증을 수행하고,
 *          검증을 통과한 경우에만 Lion 객체를 생성하는 흐름.
 *
 *  ▶ 책임 분리 관점에서는 좋지 않은 구조이다.
 *    유효성 규칙이 main에 흩어져 있어 Lion을 다른 곳에서 사용하면 검증을 또 작성해야 한다.
 */
public class Mission02Step1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("이름을 입력하세요: ");
        String name = sc.nextLine();

        System.out.print("전공을 입력하세요: ");
        String major = sc.nextLine();

        System.out.print("기수를 입력하세요: ");
        int generation = Integer.parseInt(sc.nextLine().trim());

        // ── main이 직접 유효성 검증 ───────────────────────────────
        if (name == null || name.trim().isEmpty()) {
            System.out.println("[입력 오류] 이름은 빈 값일 수 없습니다.");
            return;
        }
        if (major == null || major.trim().isEmpty()) {
            System.out.println("[입력 오류] 전공은 빈 값일 수 없습니다.");
            return;
        }
        if (generation < 1) {
            System.out.println("[입력 오류] 기수는 1 이상이어야 합니다.");
            return;
        }
        // ─────────────────────────────────────────────────────────

        // 검증 통과 → Lion 객체 생성
        Lion lion = new Lion(name, major, generation);
        lion.introduce();
    }
}
