import java.util.Scanner;

public class Mission01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int count = 0;
        while (count < 5) {
            System.out.print("아기사자 인원 수를 입력하세요: ");
            count = scanner.nextInt();
            if (count < 5) {
                System.out.println("5명 미만입니다. 다시 입력해주세요!");
            }
        }

        String[] names = new String[count];
        scanner.nextLine(); // 버퍼 비우기

        for (int i = 0; i < count; i++) {
            System.out.print((i + 1) + "번째 아기사자 이름: ");
            names[i] = scanner.nextLine();
        }

        System.out.println("\n===== 아기사자 명단 =====");
        for (int i = 0; i < names.length; i++) {
            System.out.println((i + 1) + ". " + names[i]);
        }
        System.out.println("총 " + count + "명");

        scanner.close();
    }
}
