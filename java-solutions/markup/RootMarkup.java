package markup;

import java.util.List;

public abstract class RootMarkup {
    protected List<Markup> nodes;


    public RootMarkup(List<Markup> nodes) {
        this.nodes = nodes;
    }

    public abstract String getMarkdown();

    protected abstract String getTag();

    public void toMarkdown(StringBuilder sb) {
        sb.append(getMarkdown());
        for (Markup node: nodes) {
            node.toMarkdown(sb);
        }
        sb.append(getMarkdown());
    }



    public void toHtml(StringBuilder sb) {
        final String tag = getTag();

        if (!tag.isBlank()) {
            sb.append("<").append(tag).append(">");
        }
        for (Markup node:  nodes) {
            node.toHtml(sb);
        }
        if (!tag.isBlank()) {
            sb.append("</").append(tag).append(">");
        }
    }
}
