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

import de.edgelord.sanjo.SJAddress;
import de.edgelord.sanjo.SJClass;
import de.edgelord.sanjo.SJValue;
import de.edgelord.sanjo.SanjoParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    private static List<String> testContent = new ArrayList<>();

    @BeforeAll
    @DisplayName("Feed valid sanjo lines to the list")
    public static void initTestContent() {
        testContent = new ArrayList<>();

        testContent.add(".key=value");
        testContent.add(".1=2");
        testContent.add(".eminem=Marshall Mathers");
        testContent.add(":empty_class");
        testContent.add(".me=Malte Dostal");
        testContent.add(":empty-class2");
        testContent.add(":class");
        testContent.add("    .subvalue=here");
        testContent.add("    .something=nothing");
        testContent.add("    :subclass");
        testContent.add("        .subsubvalue=Hello There");
        testContent.add(".something=meh");
    }

    @Test
    @DisplayName("Test reading classes and values from the list of lines")
    public void testRead() {
        final SanjoParser parser = new SanjoParser();
        final SJClass root = parser.parse(testContent);
        assertEquals("value", root.getValue("key").getValue());
        assertEquals("2", root.getValue("1").getValue());
        assertEquals("Marshall Mathers", root.getValue("eminem").getValue());
        assertEquals("Malte Dostal", root.getValue("me").getValue());
        assertEquals("meh", root.getValue("something").getValue());

        assertEquals("Hello There", ((SJValue) root.get(SJAddress.forString(":class:subclass.subsubvalue"))).getValue());
    }
}
