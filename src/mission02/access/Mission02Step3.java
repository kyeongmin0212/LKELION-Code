package mission02.access;   // ← Lion 과 다른 패키지!

import mission02.Lion;

/**
 * Step 3 — 다른 패키지에서 Lion 의 필드에 직접 접근을 시도해
 *          접근 제어자(public / default / private)의 효과를 코드로 확인한다.
 *
 *  ※ setter / getter 우회 없이 "필드에 직접" 접근만 시도한다.
 *  ※ 컴파일 오류가 나는 줄은 주석 처리해 두었으며, 주석을 해제하면
 *    javac 가 실제로 어떤 메시지를 내는지 그 자리에 함께 적어 두었다.
 */
public class Mission02Step3 {

    public static void main(String[] args) {
        Lion lion = Lion.create("김라이언", "백엔드", 13);

        // ─────────────────────────────────────────────────────────
        // ① public 필드  →  다른 패키지에서도 정상 접근
        // ─────────────────────────────────────────────────────────
        System.out.println("[OK]  public  name        = " + lion.name);

        // ─────────────────────────────────────────────────────────
        // ② default(package-private) 필드  →  컴파일 오류
        // ─────────────────────────────────────────────────────────
        // System.out.println("[??] default major      = " + lion.major);
        //   └ 주석을 해제하면 javac 결과:
        //     error: major is not public in Lion; cannot be accessed from outside package

        // ─────────────────────────────────────────────────────────
        // ③ private 필드  →  컴파일 오류
        // ─────────────────────────────────────────────────────────
        // System.out.println("[??] private generation = " + lion.generation);
        //   └ 주석을 해제하면 javac 결과:
        //     error: generation has private access in Lion

        System.out.println();
        System.out.println("→ 결론: 다른 패키지에서 직접 접근 가능한 필드는 public 뿐이다.");
        System.out.println("        default 는 같은 패키지에서만, private 은 같은 클래스에서만 접근 가능.");
    }
}
