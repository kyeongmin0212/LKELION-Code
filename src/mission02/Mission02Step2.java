package mission02;

import java.util.Scanner;

/**
 * Step 2 — 유효성 검증 로직을 Lion 클래스로 이동한 흐름.
 *
 *  ▶ main은 이제 입력 / 흐름 제어 / 객체 호출만 담당한다.
 *    유효성 판단·정보 관리는 Lion이 책임진다 (캡슐화 + 단일 책임).
 *  ▶ Lion.create(...) 내부에서 검증이 실패하면 IllegalArgumentException이 던져진다.
 */
public class Mission02Step2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("이름을 입력하세요: ");
        String name = sc.nextLine();

        System.out.print("전공을 입력하세요: ");
        String major = sc.nextLine();

        System.out.print("기수를 입력하세요: ");
        int generation = Integer.parseInt(sc.nextLine().trim());

        try {
            Lion lion = Lion.create(name, major, generation);   // 검증은 Lion이 한다
            lion.introduce();
        } catch (IllegalArgumentException e) {
            System.out.println("[입력 오류] " + e.getMessage());
        }
    }
}
