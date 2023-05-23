package expression;

public class Multiply extends BinaryOperation {

    public Multiply(BaseExpression firstOperand, BaseExpression secondOperand) {
        super(firstOperand, secondOperand, (op1, op2) -> op1 * op2, "*");
    }
}
