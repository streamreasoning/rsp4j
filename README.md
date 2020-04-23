# Yet Another RDF Stream Processing Engine: YASPER

YASPER is a library to build RDF Stream Processing (RSP) Engines according with the reference model RSP-QL [1](http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf). 

YASPER is inspired by the OWL API, and other work that aim at spreading the Semantic Web (Stream Reasoning) research by means of practical and usable software tools.

In this repository, two Maven projects are present:

- yasper-core, which contains the interfaces and abstractions required to develop your RSP engine.
- yasper-simple, which is an reference implementation that aims at showing yasper-core's usage by providing it.polimi.deib.rsp.test.examples.

Futher adoption of YASPER will be listed below. A on-going [documentation](https://github.com/riccardotommasini/yasper/wiki) is also available. 

YASPER is an open and ongoing project. Welcome adoption as well as suggestion or request.

```xml
 <repositories>
   <repository>
    <id>jitpack.io</id>
       <url>https://jitpack.io</url>
   </repository>
 </repositories>
<dependency>
   <groupId>com.github.riccardotommasini.yasper</groupId>
     <artifactId>yasper</artifactId>
       <version>${tag}</version>
 </dependency>
```

[![DOI](https://zenodo.org/badge/64671163.svg)](https://zenodo.org/badge/latestdoi/64671163)


[1] [http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf](http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf)
