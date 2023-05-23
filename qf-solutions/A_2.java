import java.util.Scanner;

public class A_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double n = sc.nextDouble();
        double result = 2 * Math.ceil((n - b) / (b - a)) + 1;
        System.out.println((int) result);
    }
}
