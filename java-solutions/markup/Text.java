package markup;

import java.util.Collections;

public class Text extends Markup {

    private final String text;

    public Text(String text) {
        super(Collections.emptyList());
        this.text = text;
    }

    @Override
    public void toHtml(StringBuilder sb){
        sb.append(text);
    }

    @Override
    public String getMarkdown() {
        return "";
    }

    @Override
    protected String getTag() {
        return "";
    }

    @Override
    public void toMarkdown(StringBuilder sb){
        sb.append(text);
    }
}
