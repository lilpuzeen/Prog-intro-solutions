package markup;

import java.util.List;

public abstract class ParentMarkdown extends RootMarkup{

    public ParentMarkdown(List<Markup> nodes) {
        super(nodes);
    }
}
