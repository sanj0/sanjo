/*
 * Copyright 2022 Malte Dostal
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

package de.sanj0.sanjo;

/**
 * Stores meta information about sanjo file formatting.
 */
public class MetaInf {

    /**
     * The default instance, with
     * all default values
     */
    public static final MetaInf DEFAULT_META_INF = new MetaInf();

    /**
     * The width of the indention
     */
    protected int indentionWidth;

    /**
     * The suffix of a key name to
     * make it recognized as a list
     * by the {@link SanjoParser}
     */
    protected String listSuffix;

    /**
     * The separator between individual
     * list items to be recognized by
     * the {@link SanjoParser} as such
     */
    protected String listSeparator;

    /**
     * The constructor
     *
     * @param indentionWidth the {@link #indentionWidth}
     * @param listSuffix the {@link #listSuffix}
     * @param listSeparator the {@link #listSeparator}
     */
    public MetaInf(final int indentionWidth, final String listSuffix, final String listSeparator) {
        this.indentionWidth = indentionWidth;
        this.listSuffix = listSuffix;
        this.listSeparator = listSeparator;
    }

    /**
     * All-default constructor.
     */
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
