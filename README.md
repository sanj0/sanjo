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

```xml
<dependencies>
    <dependency>
        <groupId>de.edgelord</groupId>
        <artifactId>sanjo</artifactId>
        <version>0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```


