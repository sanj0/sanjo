# sanjo

sanjo is a specification for an "object-orientated" key-value text format. "object orientated" as in: each key-value pair belongs to a class, classes can have subclasses.
This repository also contains a java library for reading and writing sanjo data.

### The format

```bash
:class
    .key=value
    :subclass
        .key=value
    .key=value
    :subclass
        :subclass
            .key=value
```

Class-affiliation is based on indention; every class and key-value pair with zero indention is a direct child of the `default` class. 

### The sanjo java library

The library is built using maven, adding it as a dependency is thus as simple as adding the following snippet to your project's pom.xml file:

```xml
<dependencies>
    <dependency>
        <groupId>de.edgelord</groupId>
        <artifactId>sanjo</artifactId>
        <version>0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

#### Usage examples

```java
import de.edgelord.sanjo.*;


public static void main(String[] args) {
    // parsing a sanjo dom from a list of sanjo "lines"
    final Parser listParser = new Parser();
    final SJClass listDataRoot.parse(Arrays.asList(".key=value"));

    // parsing a sanjo file
    final SanjoFile file = new SanjoFile("/path/to/file.sj");
    final Parser fileParser = file.parser();
    final SJClass fileDataRoot = fileParser.parse();

    // the SJClass return by Parser#parse(*)
    // is the data's "default" class, therefore 
    // every key-value pair and every defined class
    // is a direct of indirect child of the return "dataRoot"
}
```


