package markup;

import java.util.List;

public class Emphasis extends Markup {

    public Emphasis(List<Markup> nodes) {
        super(nodes);
    }

    @Override
    public String getMarkdown() {
        return "*";
    }

    @Override
    protected String getTag() {
        return "em";
    }
}
