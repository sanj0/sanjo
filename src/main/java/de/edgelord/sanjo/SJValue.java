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

import java.util.ArrayList;
import java.util.List;

public class SJValue extends Number {

    private String key;
    private Object value;

    public SJValue(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public String write(final MetaInf metaInf) {
        final StringBuilder builder = new StringBuilder();
        builder.append(SanjoParser.KEY_PREFIX).append(getKey());

        if (value instanceof List) {
            final String separator = metaInf.getListSeparator();
            builder.append(metaInf.getListSuffix());
            builder.append(SanjoParser.ASSIGNMENT_OPERATOR);
            final List list = (List) value;
            for (final Object o : list) {
                builder.append(o.toString() + separator);
            }
            builder.delete(builder.length() - separator.length(), builder.length());
        } else {
            builder.append(SanjoParser.ASSIGNMENT_OPERATOR);
            builder.append(value.toString());
        }
        return builder.toString();
    }

    public boolean booleanValue() {
        return Boolean.parseBoolean(string());
    }

    public List<String> getList() {
        return (List<String>) value;
    }

    public < T > List<T> typedList() {
        final List<String> stringList = getList();
        final List<T> list = new ArrayList<>();

        for (final String s : stringList) {
            T entry = null;
            try {
                entry = (T) s;
            } catch (final Exception ex) {
                throw new RuntimeException("cannot convert " +
                        s + " to type " + ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0].getTypeName());
            }
            list.add(entry);
        }

        return list;
    }

    /**
     * Used to force generic type.
     * The actually argument is fully ignored
     * and only used to force a specific generic type.
     *
     * @param type a dummy value to force a specific typed
     * @param <T> the type of element in the returns typed list
     * @return a typed-list representation of this value
     * @see #typedList()
     */
    public < T > List<T> typedList(final T type) {
        return typedList();
    }

    @Override
    public String toString() {
        return string();
    }

    @Override
    public int intValue() {
        return Integer.parseInt(string());
    }

    @Override
    public long longValue() {
        return Long.parseLong(string());
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(string());
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(string());
    }

    public String string() {
        return value.toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public static class Empty extends SJValue {
        public Empty() {
            super("", new Object());
        }
    }
}
