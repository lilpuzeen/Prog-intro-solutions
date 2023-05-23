package expression;

public class Add extends BinaryOperation {

    public Add(BaseExpression firstOperand, BaseExpression secondOperand) {
        super(firstOperand, secondOperand, (op1, op2) -> op1 + op2, "+");
    }
}
