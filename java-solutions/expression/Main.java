package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        );
        System.out.println("Enter x:");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        System.out.printf("The result for the expression %s is %d", e, e.evaluate(x));
    }
}
