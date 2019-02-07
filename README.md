# Digipost JAXB2 Jackson Helper

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.digipost.jaxb/jaxb2-jackson-helper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.digipost.jaxb/jaxb2-jackson-helper)
[![Build Status](https://travis-ci.org/digipost/jaxb2-jackson-helper.svg?branch=master)](https://travis-ci.org/digipost/jaxb2-jackson-helper)

In order to help Jackson a bit, this JAXB2/XJC plugin will add `@XmlElement(nillable=false)` if `nillable=false` and `minoccurs=0` is set in XSD.


## Getting the plugin

If using Maven you will probably enable the plugin in the configuration of your JAXB2 plugin.
For instance, if you use [maven-jaxb2-plugin](https://github.com/highsource/maven-jaxb2-plugin), this will enable the plugin in your build:

```xml
<plugin>
  <groupId>org.jvnet.jaxb2.maven2</groupId>
  <artifactId>maven-jaxb2-plugin</artifactId>
  <version><!-- TODO: Replace with newest compatible version number --></version>
  <configuration>
    <args>
      <arg>-Xjacksonfive</arg>
      ...
    </args>
    <plugins>
      <plugin>
        <groupId>no.digipost.jaxb</groupId>
        <artifactId>jaxb2-jackson-helper</artifactId>
        <version><!-- TODO: Replace with newest version number --></version>
      </plugin>
...
```


## License

Digipost JAXB2 Jackson Helper is licensed under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
