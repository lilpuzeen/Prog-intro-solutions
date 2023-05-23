package markup;

import java.util.List;

public class Strikeout extends Markup {

    public Strikeout(List<Markup> nodes) {
        super(nodes);
    }

    @Override
    public String getMarkdown() {
        return "~";
    }

    @Override
    protected String getTag() {
        return "s";
    }
}
