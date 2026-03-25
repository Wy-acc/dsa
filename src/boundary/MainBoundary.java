package boundary;//author: Yaw Wei Ying, Tay Mian Yin, Tay Zhuang Yin, Yoo Xin Wei

import java.util.Scanner;

public class MainBoundary {
    private final Scanner scanner;

    public MainBoundary() {
        scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("\n=== INTERNSHIP MANAGEMENT SYSTEM ===");
        System.out.println("1. Job Management");
        System.out.println("2. Applicant Management");
        System.out.println("3. Matching Engine");
        System.out.println("4. Interview Scheduling");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
        return scanner.nextInt();
    }

    public void showMessage(String message) {
        System.out.println("\n" + message);
    }
}
