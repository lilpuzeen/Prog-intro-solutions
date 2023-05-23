package markup;

import java.util.List;

public class Paragraph extends ParentMarkdown {

    private List<Markup> nodes;

    public Paragraph(List<Markup> nodes) {
        super(nodes);
    }

    @Override
    public String getMarkdown() {
        return "";
    }

    @Override
    protected String getTag() {
        return "";
    }
}
