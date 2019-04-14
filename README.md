# Cantis
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org) 

[![Build Status](https://api.travis-ci.org/korthout/Cantis.svg?branch=master)](https://travis-ci.org/korthout/Cantis)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.korthout%3Acantis&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.github.korthout%3Acantis)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.korthout%3Acantis&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.github.korthout%3Acantis)
[![codecov](https://codecov.io/gh/korthout/Cantis/branch/master/graph/badge.svg)](https://codecov.io/gh/korthout/Cantis)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.korthout/cantis.svg?label=Maven%20Central&color=blue)](https://search.maven.org/search?q=g:%22com.github.korthout%22%20AND%20a:%22cantis%22)

Cantis is a living documentation glossary generator for Java projects.
It helps to keep your glossary up-to-date by extracting it from your Java classes 
and their JavaDoc descriptions.

## Why
Cantis was created to **overcome miscommunication** about the meaning of domain-specific terms 
between co-workers (e.g. developers, managers, sales, customer heroes and so on).

It also may give developers an extra nudge to **document classes** with care. 

Lastly, Elegant Object Principles were applied, to **learn more** about true 
Object-Oriented Programming and how Functional Programming can complement OOP.
Hopefully this can lead to meaningful discussions about its applications.

## Example
Consider a class:
```java
/**
 * A person that uses our software.
 */
@Term
class User {

}
```
Cantis can turn it into a glossary: 
```
User: A person that uses our software.
```

Cantis also generates its own [glossary](glossary.txt) and 
[cantisfile](cantisfile.json) (a json formatted glossary).

## Install
In order to use the `@Term` annotation,
you can add a dependency to `cantis` to your project:

Maven:

```xml
<dependency>
    <groupId>com.github.korthout</groupId>
    <artifactId>cantis</artifactId>
    <version>0.1</version>
</dependency>
```

Gradle:

```
dependencies {
    implementation 'com.github.korthout:cantis:0.1'
}
```

> If you don't want to have this compile-time dependency,
You can also declare your own `@Term` annotation.
Just make sure to call it `@Term` and you can place it in any package you like.

## Usage
Using Cantis on your own project is easy. Simply:
* annotate a class with `@Term`
* add a JavaDoc description to the class
* type `cantis generate` in your terminal or use the maven plugin

Cantis can be used as a maven plugin and via its commandline interface (cli).

### Maven plugin
To use the maven plugin, simply add the following to the `plugins` section of 
your `pom.xml`.

```xml
<plugin>
    <groupId>com.github.korthout</groupId>
    <artifactId>cantis</artifactId>
    <version>0.1</version>
    <executions>
        <execution>
            <id>cantis</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <format>json</format>
                <target>cantisfile.json</target>
            </configuration>
        </execution>
    </executions>
</plugin>
```

> Note: Using the maven plugin requires jdk11.

> Note: Due to limitations in maven, this plugin creates a separate glossary
file for each maven module. For multi-module maven projects that wish to 
aggregate the definitions from all of the submodules into one glossary file, 
we recommend that you use the cli instead.

### Commandline interface
The cli is more flexible than the maven plugin. It does not require jdk 11 and
can be used on multi-module maven projects.

Its main usage is the generate command:
```sh
cantis generate [<options>] [--] [<source>]
    <source>: The root directory of your source code. Defaults to .
    
    Options:
    -f, --format <format>   Output format (options: json, plain; default: plain)
    -t, --target <target>   Path to output file
```

You'll need the executable jar. Check
[releases](https://github.com/korthout/Cantis/releases) for the latest version.
Download and execute it as any regular jar.
```sh
java -jar cantis-0.1.jar <command> [--] [<arguments>]
```

**Shorthand usage**
To use `cantis generate` instead of `java -jar cantis-0.1.jar generate`,
download the binary from [releases](https://github.com/korthout/Cantis/releases)
to the same folder and give it execution permissions.
```sh
chmod +x ~/downloads/cantis/cantis
```

For easy access, place the binary on your PATH environment variable.
```sh
export PATH=$PATH:~/downloads/cantis
```

## How to contribute?

Just fork the repo and send us a pull request. 
If you have any questions, simply create an issue so we can talk about it. 

**Code of Conduct**
All contributions must adhere to the [code of conduct](CODE_OF_CONDUCT.md),
which shouldn't be hard. Just be nice.

**Design principles**. 
When creating pull requests, 
please keep the [design principles](http://www.elegantobjects.org#principles) in mind.
We always look for the best solution, even if it doesn't fit these principles.
If you are unsure about whether you applied these principles correctly (or don't know how), 
please still create your pull request so we can **discuss** it and **learn** from it together.
Several exceptions to the design principles have already been made. 
For example, annotations against nullability, equals/hashcode generation, and some OO-boundry 
situations. **We welcome discussions** about these exceptions and other elegant object related 
subjects.

## Thanks
This software would not be possible without these awesome projects:

* [Airline](https://github.com/airlift/airline) - parsing Git like command line structures
* [Cactoos](https://github.com/yegor256/cactoos) - Object-Oriented Java primitives
* [Javaparser](https://github.com/javaparser/javaparser) - a simple and lightweight set of tools to 
generate, analyze, and process Java code

A full list of projects we depend on can be found in the [pom file](pom.xml).

## License (MIT)
Copyright 2018 Nico Korthout

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
