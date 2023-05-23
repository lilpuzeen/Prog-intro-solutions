package expression;

public class Const implements BaseExpression {

    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }

        if (!(o instanceof Const)) {
            return false;
        }

        Const another = (Const) o;
        if (this == another) {
            return true;
        }

        if (this.hashCode() != another.hashCode()) {
            return false;
        }

        return value == another.value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }
}
