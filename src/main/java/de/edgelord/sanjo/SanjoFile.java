package de.edgelord.sanjo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

/**
 * A sanjo file is a file to store
 * key-value pairs following a specific
 * syntax for simple read- and write-ability
 * for both humans and computers.
 * The syntax for the format is as follows:
 * <pre>
 * :class_name
 *     .key_name:type=value
 *     .key_name:type=value
 *     _sub_class-name
 *         .key_name:type=value
 * :class_name
 *     .key_name:type=value
 * </pre>
 * Class and key names may not contain the characters . and :
 * Following types are allowed: bool, int, dec, string<br>
 * and lists of said types, by adding an s to the type name:<br>
 * bools, ints, decs, strings
 * When no type is given, the default one is string:
 * <pre>.key=value</pre>
 * There is one special class by the name
 * of {@code meta}, which can specify special
 * values as follows:
 * <pre>
 * _meta
 *     .indentation:int=4
 *     .newline=\n
 *     .author=Malte Dostal
 *     .name=format
 *     .list_separator=,
 * </pre>
 * A more detailed example can be found in the file
 * {@code format.sj} at the root of the sources git repository
 * or at this url:
 * @see <a href=https://www.github.com/edgelord314/sanjo/blob/master/format.sj>https://www.github.com/edgelord314/sanjo/blob/master/format.sj</a>
 */
public class SanjoFile extends File {
    public SanjoFile(final String pathname) {
        super(pathname);
    }

    public SanjoFile(final String parent, String child) {
        super(parent, child);
    }

    public SanjoFile(final File parent, String child) {
        super(parent, child);
    }

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
        return Files.readAllLines(toPath());
    }
}
