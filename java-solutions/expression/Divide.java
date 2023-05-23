package expression;

public class Divide extends BinaryOperation {


    public Divide(BaseExpression firstOperand, BaseExpression secondOperand) {
        super(firstOperand, secondOperand, (op1, op2) -> op1 / op2, "/");
    }
}
