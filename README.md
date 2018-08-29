# Cantis
Cantis is a living glossary documentation generator.

## Usage
```
cantis [-sources <path>]
  -sources <path>: The root directory in which to search for @GlossaryTerm annotated classes
```

## Installation
Cantis can be installed as a maven plugin and as a cli application.

### Maven plugin
For maven projects a maven plugin is provided.

> Note: Due to limitations in maven, this plugin creates a separate glossary file for each maven module. For multi-module maven projects that wish to aggregate the definitions from all of the submodules into one glossary file, we recommend to use the cli approach described in [CLI application](#cli-installation).

The plugin can be used by adding the following to the `plugins` section of your `pom.xml`.

```xml
<plugin>
    <groupId>nl.korthout</groupId>
    <artifactId>cantis</artifactId>
    <version>0.0.2</version>
    <executions>
        <execution>
            <id>cantis</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>glossary</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### CLI applicaton <a name="cli-installation"></a>
Download the latest executable jar. You can simply execute it as any regular jar.
```sh
java -jar cantis-<verson>-jar-with-dependencies.jar
```

You can also use the binary provided in the bin folder of this repo. Remember to provide execution permissions for this binary, after downloading it.
```sh
chmod +x cantis
```

For easy access, place the binary on your PATH environment variable.
```sh
export PATH=$PATH:~/downloads/cantis/bin
```
