package expression;

import java.util.Objects;
public class Variable implements BaseExpression {

    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (Objects.equals(this.var, "x")) {
            return x;
        } else if (Objects.equals(this.var, "y")) {
            return y;
        } else {
            return z;
        }
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Variable)) {
            return false;
        }

        Variable another = (Variable) o;
        if (this == another) {
            return true;
        }

        if (this.hashCode() != another.hashCode()) {
            return false;
        }

        return var.equals(another.var);
    }

}
