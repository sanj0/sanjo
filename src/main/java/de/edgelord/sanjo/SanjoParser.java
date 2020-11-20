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

import java.io.IOException;
import java.util.*;

/**
 * A utility class to parse
 * a {@link SanjoFile}.
 */
public class SanjoParser {

    public static final char CLASS_PREFIX = ':';
    public static final char KEY_PREFIX = '.';
    public static final String ASSIGNMENT_OPERATOR = "=";
    public static final String DEFAULT_LIST_KEY_SUFFIX = "[]";
    public static final String DEFAULT_LIST_SEPARATOR = ",";
    public static final int DEFAULT_INDENTION_WIDTH = 4;

    public static final String INDENTATION_WIDTH_KEY = "indentation";
    public static final String NEWLINE_KEY = "newline";
    public static final String AUTHOR_KEY = "author";
    public static final String NAME_KEY = "name";
    public static final String LIST_KEY_SUFFIX_KEY = "list_suffix";
    public static final String LIST_SEPARATOR = "list_separator";
    public static final char SPACE = ' ';

    private final SanjoFile file;
    private final MetaInf metaInf;
    private int lastIndentLevel = 0;
    private SJClass defaultClass = SJClass.defaultClass();
    private Map<Integer, SJClass> workingClasses = new HashMap<>();

    public SanjoParser(final SanjoFile file) {
        this.file = file;
        metaInf = new MetaInf(DEFAULT_INDENTION_WIDTH, DEFAULT_LIST_KEY_SUFFIX, DEFAULT_LIST_SEPARATOR);
    }

    public SanjoParser() {
        this(null);
    }

    private void parse0(final List<String> lines) {
        workingClasses.put(0, defaultClass);
        int lineNumber = 1;
        for (final String rawLine : lines) {
            String line = removeLeadingSpaces(rawLine);
            int currentIndent = rawLine.length() - line.length();
            int currentIndentLevel = currentIndent / metaInf.indentionWidth + 1;

            if(line.startsWith(String.valueOf(CLASS_PREFIX))) {
                // class definition
                checkIndention(currentIndent, lineNumber);
                if (currentIndentLevel > lastIndentLevel) {
                    throw indentionError(lineNumber);
                }
                final SJClass newClass = new SJClass(line.replaceFirst(String.valueOf(CLASS_PREFIX), ""));
                if (currentIndentLevel == lastIndentLevel) {
                    // case 1: current indent is equal to the last indent -
                    // new class should be a direct subclass of the current class'
                    // parent, or, in case the indent is 0, a direct child of the
                    // default class
                    final SJClass parent = currentIndentLevel == 0 ? defaultClass : workingClasses.get(currentIndentLevel - 1);
                    newClass.parentClass = parent;
                    parent.getChildren().add(newClass);
                } else if (currentIndentLevel < lastIndentLevel) {
                    // case 2: current indent is smaller than the last indent
                    // new class should be a subclass of some parent of some parent
                    // of the current class, depending on the indention delta
                    final int delta = lastIndentLevel - currentIndentLevel;
                    SJClass parent = workingClasses.get(currentIndentLevel - 1);
                    parent.getChildren().add(newClass);
                    newClass.parentClass = parent;
                } else {
                    // you cannot indent a class definition
                    throw indentionError(lineNumber);
                }
                workingClasses.put(currentIndentLevel, newClass);
                // increment current indent
                // because any following classes or
                // k-v pairs have to be indented
                // - allow for empty classes?
                currentIndentLevel++;
            } else if (line.startsWith(String.valueOf(KEY_PREFIX))) {
                // key-value pair definition
                checkIndention(currentIndent, lineNumber);
                // TODO: check class affiliation by indention-level
                final SJValue value = createValue(line);
                workingClasses.get(currentIndentLevel - 1).getValues().put(value.getKey(), value);
            } else {
                // everything else is ignored
                // as a comment
                continue;
            }
            lineNumber++;
            lastIndentLevel = currentIndentLevel;
        }
    }

    public SJClass parse() throws IOException {
        final List<String> lines = file.readLines();
        parse0(lines);
        return defaultClass;
    }

    public SJClass parse(final String content) {
        final List<String> lines = Arrays.asList(content.split(System.lineSeparator()));
        parse0(lines);
        return defaultClass;
    }

    public SJClass parse(final List<String> lines) {
        parse0(lines);
        return defaultClass;
    }

    private SJValue createValue(final String snippet) {
        final String[] keyAndValue = snippet.split(ASSIGNMENT_OPERATOR, 2);
        String keyString = keyAndValue[0];
        final String valueString = keyAndValue[1];
        Object value;
        if (keyString.endsWith(metaInf.listSuffix)) {
            value = new ArrayList<>(Arrays.asList(valueString.split(metaInf.listSeparator)));
            keyString = keyString.substring(0, keyString.length() - metaInf.listSuffix.length());
        } else {
            value = valueString;
        }

        return new SJValue(keyString.replaceFirst(String.valueOf(KEY_PREFIX), ""), value);
    }

    private boolean isIndentionLegal(final int indention) {
        return indention % metaInf.indentionWidth == 0;
    }

    private void checkIndention(final int indention, final int lineNumber) {
        if(!isIndentionLegal(indention)) {
            throw indentionError(lineNumber);
        }
    }

    /**
     * Removes the leading from the given
     * String.
     *
     * @return the given String but without leading spaces
     */
    private String removeLeadingSpaces(final String s) {
        int spaceCount = 0;
        for (final char c : s.toCharArray()) {
            if (c == SPACE) {
                spaceCount++;
            } else {
                break;
            }
        }

        return s.substring(spaceCount);
    }

    private SanjoParserError indentionError(final int lineNumber) {
        return new SanjoParserError(SanjoParserError.INDENTION_ERROR_MESSAGE, lineNumber);
    }

    public SanjoFile getFile() {
        return file;
    }

    public MetaInf getMetaInf() {
        return metaInf;
    }

    public class SanjoParserError extends RuntimeException {
        private static final String INDENTION_ERROR_MESSAGE = "Illegal indention";
        public SanjoParserError(final String message, final int lineNumber) {
            super("\n    Error parsing file " + file.getAbsolutePath() + ": "
                    + message + " in line " + lineNumber);
        }
    }
}
