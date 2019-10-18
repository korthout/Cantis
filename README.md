# Cantis
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org) 

[![Build Status](https://api.travis-ci.org/korthout/Cantis.svg?branch=master)](https://travis-ci.org/korthout/Cantis)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.korthout.cantis%3Afrom-java&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.github.korthout.cantis%3Afrom-java)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.korthout.cantis%3Afrom-java&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.github.korthout.cantis%3Afrom-java)
[![codecov](https://codecov.io/gh/korthout/Cantis/branch/master/graph/badge.svg)](https://codecov.io/gh/korthout/Cantis)
[![Hits-of-Code](https://hitsofcode.com/github/korthout/Cantis)](https://hitsofcode.com/view/github/korthout/Cantis)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.korthout/cantis.svg?label=Maven%20Central&color=blue)](https://search.maven.org/search?q=g:%22com.github.korthout%22%20AND%20a:%22cantis%22)

Cantis is a living documentation glossary extractor. It helps to keep your 
glossary up-to-date by extracting it from the documentation in your code.

## Why
Cantis was created to **overcome miscommunication** about the meaning of 
domain-specific terms between co-workers (e.g. developers, managers, sales, 
customer heroes and so on).

It also may give developers an extra nudge to **document classes** with care.

Lastly, Elegant Object Principles are applied, to **learn more** about "true" 
Object-Oriented Programming and how Functional Programming can complement OOP.
Hopefully this can lead to meaningful discussions about its applications.

## Example
Currently, the Cantis [from-java](from-java) extractor is the heart of Cantis.
It can extract a glossary from your Java codebase.

Consider a Java class:
```java
/**
 * A person that uses our software.
 */
@Term
class User {

}
```
From-java can turn it into a glossary: 
```
User: A person that uses our software.
```

Cantis from-java also generates its own [glossary](glossary.txt) and 
[cantisfile.json](cantisfile.json).

## Install and usage
Take a look at the [README](from-java/README.md) of the from-java extractor.

## Cantisfile
Cantis is being rebuild to become a set of tools build around the `cantisfile`.

Cantisfiles contain everything that make-up a glossary. Its schema actually
describes of what glossaries look like.

```typescript
// Typescript definition of a cantisfile
interface Cantisfile {
    definitions: Definition[]
};
interface Definition {
    term: string
    description: string
};
```

## How to contribute?
Just fork the repo and send us a pull request. 
If you have any questions, simply create an issue so we can talk about it. 

**Code of Conduct**
All contributions must adhere to the [code of conduct](CODE_OF_CONDUCT.md),
which shouldn't be hard. Just be nice.

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
