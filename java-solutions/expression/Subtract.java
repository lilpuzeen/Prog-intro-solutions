package expression;

public class Subtract extends BinaryOperation {

    public Subtract(BaseExpression firstOperand, BaseExpression secondOperand) {
        super(firstOperand, secondOperand, (op1, op2) -> op1 - op2, "-");
    }
}
