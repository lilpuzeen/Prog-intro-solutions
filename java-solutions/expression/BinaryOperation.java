package expression;

import java.util.Objects;
import java.util.function.BiFunction;

public abstract class BinaryOperation implements BaseExpression {

    private final BiFunction<Integer, Integer, Integer> function;
    private final String operationSign;
    private final BaseExpression firstOperand;
    private final BaseExpression secondOperand;

    public BinaryOperation(BaseExpression firstOperand, BaseExpression secondOperand,
                           BiFunction<Integer, Integer, Integer> function, String sign) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.function = function;
        this.operationSign = sign;
    }

    public int evaluate(int x, int y, int z) {
        return function.apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

    public int evaluate(int x) {
        return function.apply(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

    @Override
    public String toString() {
        return new StringBuilder("(")
                .append(firstOperand.toString())
                .append(" ")
                .append(operationSign)
                .append(" ")
                .append(secondOperand.toString())
                .append(")")
                .toString();
    }

    public int hashCode() {
        return 37 * (operationSign.hashCode() + 31 * (firstOperand.hashCode() + 29 * secondOperand.hashCode()));
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof BinaryOperation)) {
            return false;
        }

        BinaryOperation another = (BinaryOperation) o;
        if (this == another) {
            return true;
        }

        if (this.hashCode() != another.hashCode()) {
            return false;
        }

        return this.firstOperand.equals(another.firstOperand)
                && this.secondOperand.equals(another.secondOperand)
                && this.operationSign.equals(another.operationSign);
    }
}
