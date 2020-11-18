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
