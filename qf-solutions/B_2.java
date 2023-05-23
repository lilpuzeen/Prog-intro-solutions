import java.util.Scanner;

public class B_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int distinct = -710 * 25000;
        while (n > 0) {
            n--;
            distinct += 710;
            System.out.println(distinct);
        }
    }
}
