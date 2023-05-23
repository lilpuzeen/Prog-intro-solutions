package markup;

import java.util.List;

public abstract class Markup extends RootMarkup{
    public Markup(List<Markup> nodes) {
        super(nodes);
    }
}
