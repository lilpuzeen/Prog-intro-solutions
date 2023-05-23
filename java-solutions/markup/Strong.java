package markup;

import java.util.List;

public class Strong extends Markup {

    public Strong(List<Markup> nodes) {
        super(nodes);
    }

    @Override
    public String getMarkdown() {
        return "__";
    }

    @Override
    protected String getTag() {
        return "strong";
    }
}
