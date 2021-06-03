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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * A sanjo file is a file to store key-value pairs in classes following a
 * specific syntax for simple read- and write-ability for both humans and
 * computers. The syntax for the format is as follows:
 * <pre>
 * :class_name
 *     .key_name:type=value
 *     .key_name:type=value
 *     :sub_class-name
 *         .key_name:type=value
 * :class_name
 *     .key_name:type=value
 * </pre>
 * Class and key names may not contain the characters . and : (see {@link
 * SJAddress})
 * <p>Values are interpreted as string literals
 * or lists separated by {@link SanjoParser#DEFAULT_LIST_SEPARATOR}.
 * <p>There is one special class by the name
 * of {@code meta}, which can specify special values as follows:
 * <pre>
 * :meta
 *     .indentation=4
 *     .newline=\n
 *     .author=Malte Dostal
 *     .name=format
 *     .list_separator=,
 * </pre>
 * A more detailed example can be found in the file {@code format.sj} at the
 * root of the sources git repository or at this url:
 *
 * @see <a href=https://www.github.com/edgelord314/sanjo/blob/master/format.sj>https://www.github.com/edgelord314/sanjo/blob/master/format.sj</a>
 */
public class SanjoFile extends File {

    /**File extension without the dot*/
    public static final String SJ_EXTENION = "sj";
    /**File xetension with the dot*/
    public static final String FILE_EXTENSION = ".sj";
    /**
     * Instantiates the file using the
     * {@link File#File(String)} constructor.
     *
     * @param pathname the path to the file
     */
    public SanjoFile(final String pathname) {
        super(pathname);
    }

    /**
     * Instantiates the file using the
     * {@link File#File(String, String)}
     * constructor.
     *
     * @param parent the path to the parent dir
     * @param child the path to the file relative to the parent dir
     */
    public SanjoFile(final String parent, final String child) {
        super(parent, child);
    }

    /**
     * Instantiates the file using the
     * {@link File#File(File, String)}
     * constructor.
     *
     * @param parent the parent dir
     * @param child the path to the file relative to the parent dir
     */
    public SanjoFile(final File parent, final String child) {
        super(parent, child);
    }

    /**
     * Instantiates the file using the
     * {@link File#File(URI)}
     * constructor.
     *
     * @param uri the uri pointing to the file
     */
    public SanjoFile(final URI uri) {
        super(uri);
    }

    /**
     * Creates a new {@link SanjoParser}
     * with <code>this</code> as the subject file
     * and returns it.
     *
     * @return a {@link SanjoParser} subjected to this file
     */
    public SanjoParser parser() {
        return new SanjoParser(this);
    }

    /**
     * Reads all lines from this file and
     * returns them as a list.
     *
     * @return all lines of this file
     * @throws IOException when something goes wrong reading the file
     */
    public List<String> readLines() throws IOException {
        return exists() ? Files.readAllLines(toPath()) : new ArrayList<>();
    }

    /**
     * Reads all lines from the given file
     * and returns them as an ArrayList or
     * returns an empty ArrayList if the file
     * doesn't exist.
     *
     * @param f a file
     * @return all lines form the given file as an ArrayList or an empty one
     * in case the file doesn't exist
     * @throws IOException if something goes wrong
     */
    public static List<String> readLines(final File f) throws IOException {
        return f.exists() ? Files.readAllLines(f.toPath()) : new ArrayList<>();
    }
}
