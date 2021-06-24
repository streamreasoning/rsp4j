# RDF Stream Processing for Java (RSP4J)

[![DOI](https://zenodo.org/badge/322566440.svg)](https://zenodo.org/badge/latestdoi/322566440)

RSP4J is a library to build RDF Stream Processing (RSP) Engines according with the reference model
RSP-QL [1](http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf).

RSP4J is inspired by the OWL API, and other work that aim at spreading the Semantic Web (Stream Reasoning) research by
means of practical and usable software tools.

In this repository, the following projects are present:

* [API](./api/Readme.md), which contains the interfaces and abstractions required to develop your RSP engine.
* [yasper](./yasper/Readme.md), which is an reference implementation that aims at showing the API usage by providing
  org.streamreasoning.rsp4j.yasper.examples.

Futher adoption of YASPER will be listed below.

A on-going [documentation](https://github.com/streamreasoning/rsp4j/wiki) is also available.

RSP4J and YASPER are open and ongoing projects. Welcome adoption as well as suggestion or request.

## Install Using Maven

```xml
 <repositories>
   <repository>
    <id>jitpack.io</id>
       <url>https://jitpack.io</url>
   </repository>

<dependency>
  <groupId>com.github.streamreasoning.rsp4j</groupId>
  <artifactId>api</artifactId>
   <version>1.0.0</version>
</dependency>
  
and 
  
<dependency>
  <groupId>com.github.streamreasoning.rsp4j</groupId>
  <artifactId>yasper</artifactId>
   <version>1.0.0</version>
</dependency>
```
