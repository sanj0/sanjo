/*
 * Copyright 2020 Malte Dostal
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.edgelord.sanjo;

import java.util.*;

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

    public < T > Optional<T> get(final SJAddress address) {
        return (Optional<T>) address.find(this);
    }

    public Optional<SJClass> getChild(final String name) {
        for (int i = 0; i < children.size(); i++) {
            final SJClass classI = children.get(i);
            if (classI.getName().equals(name)) {
                return Optional.of(classI);
            }
        }
        return Optional.empty();
    }

    public SJValue addValue(final SJValue val) {
        values.put(val.getKey(), val);
        return val;
    }

    public SJValue addValue(final String key, final Object value) {
        return addValue(new SJValue(key, value));
    }

    public SJValue addValue(final String key) {
        return addValue(new SJValue(key, ""));
    }

    public SJClass addChild(final String name) {
        final SJClass child = new SJClass(name, this, metaInf);
        children.add(child);

        return child;
    }

    public SJClass addChild(final SJClass clazz) {
        clazz.parentClass = this;
        children.add(clazz);

        return clazz;
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

    public Optional<SJValue> getValue(final String key) {
        return Optional.ofNullable(values.get(key));
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

    public MetaInf getMetaInf() {
        return metaInf;
    }
}
