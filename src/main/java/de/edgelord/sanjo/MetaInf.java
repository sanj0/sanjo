package de.edgelord.sanjo;

public class MetaInf {

    public static final MetaInf DEFAULT_META_INF = new MetaInf();

    protected int indentionWidth;
    protected String listSuffix;
    protected String listSeparator;

    public MetaInf(int indentionWidth, String listSuffix, String listSeparator) {
        this.indentionWidth = indentionWidth;
        this.listSuffix = listSuffix;
        this.listSeparator = listSeparator;
    }

    public MetaInf() {
        this(SanjoParser.DEFAULT_INDENTION_WIDTH, SanjoParser.DEFAULT_LIST_KEY_SUFFIX, SanjoParser.DEFAULT_LIST_SEPARATOR);
    }

    public int getIndentionWidth() {
        return indentionWidth;
    }

    public String getListSuffix() {
        return listSuffix;
    }

    public String getListSeparator() {
        return listSeparator;
    }
}
