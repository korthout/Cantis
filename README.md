# Cantis
Cantis is a living documentation glossary generator for Java projects.

## Usage
Cantis can be used via its commandline interface (cli) and as a maven plugin.

### Source code term definitions
Applying Cantis to your own project is easy. Simply:
* add a JavaDoc description to your domain classes.
* annotate those classes with `@GlossaryTerm`.
* generate the glossary using the cli or the maven plugin.

### Commandline interface
Main usage via our cli is:
```sh
cantis generate [--] [<source>]
    <source>: The root directory of your source code. Defaults to .
```

Download the latest executable jar. 
See `releases` for the latest version.
You can simply execute it as any regular jar.
```sh
java -jar cantis-<verson>-jar-with-dependencies.jar <command> [--] [<arguments>]
```

#### Binary
You can also use the binary provided in the bin folder of this repo.  
Remember to provide execution permissions for this binary, after downloading it.
```sh
chmod +x ./bin/cantis
```

For easy access, place the binary on your PATH environment variable.
```sh
export PATH=$PATH:~/downloads/cantis/bin
```

### Maven plugin
For maven projects a maven plugin is provided. 

> Currently, Cantis is not yet available from maven-central, 
so for now you need to install Cantis to your local repository 
before adding the plugin to your project. 
This can be done by cloning the project and running `mvn install`.

The plugin can be used by adding the following to the `plugins` section of your `pom.xml`.

```xml
<plugin>
    <groupId>nl.korthout</groupId>
    <artifactId>cantis</artifactId>
    <version>latest</version>
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

> Note: Due to limitations in maven, 
this plugin creates a separate glossary file for each maven module. 
For multi-module maven projects that wish to aggregate the definitions from all of the submodules 
into one glossary file, we recommend that you use the cli instead.