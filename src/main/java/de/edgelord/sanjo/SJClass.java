package de.edgelord.sanjo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class in a {@link SanjoFile}
 * that holds a map of {@link SJValue values}.
 */
public class SJClass {

    public static final String DEFAULT = "default";
    public static final String META = "meta";

    private String name;
    private final MetaInf metaInf;
    private Map<String, SJValue> values = new HashMap<>();
    private List<SJClass> children = new ArrayList<>();
    SJClass parentClass = null;

    public SJClass(final String name, final SJClass parentClass, final MetaInf metaInf) {
        this.name = name;
        this.parentClass = parentClass;
        this.metaInf = metaInf;
    }

    public SJClass(final String name, final MetaInf metaInf) {
        this(name, null, metaInf);
    }

    public SJClass(final String name) {
        this(name, null, MetaInf.DEFAULT_META_INF);
    }

    public String write(final String... startIndention) {
        return write(metaInf, startIndention);
    }

    public String write(final MetaInf metaInf, final String... startIndention) {
        final StringBuilder builder = new StringBuilder();
        final int indentionWidth = metaInf.getIndentionWidth();
        String indention = startIndention.length == 0 ? "" : startIndention[0];
        if(parentClass != null) {
            builder.append(indention);
            builder.append(SanjoParser.CLASS_PREFIX).append(name).append(System.lineSeparator());
            for (int i = 0; i < indentionWidth; i++) indention += SanjoParser.SPACE;
        }
        for (final SJValue value : values.values()) {
            builder.append(indention);
            builder.append(value.write(metaInf)).append(System.lineSeparator());
        }

        for (final SJClass child : children) {
            builder.append(child.write(metaInf, indention));
        }

        return builder.toString();
    }

    /**
     * Creates a new instance of this class
     * with the {@link #DEFAULT default} name
     * and returns it.
     *
     * @return a new <code>SJClass</code> with the {@link #DEFAULT default} name
     */
    public static SJClass defaultClass() {
        return new SJClass(DEFAULT);
    }

    public SJValue getValue(final String key) {
        return values.get(key);
    }

    public SJClass getParentClass() {
        return parentClass;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<String, SJValue> getValues() {
        return values;
    }

    public void setValues(final Map<String, SJValue> values) {
        this.values = values;
    }

    public List<SJClass> getChildren() {
        return children;
    }

    public void setChildren(final List<SJClass> children) {
        this.children = children;
    }
}
