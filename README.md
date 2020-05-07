# Yet Another RDF Stream Processing Engine: YASPER

YASPER is a library to build RDF Stream Processing (RSP) Engines according with the reference model RSP-QL [1](http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf). 

YASPER is inspired by the OWL API, and other work that aim at spreading the Semantic Web (Stream Reasoning) research by means of practical and usable software tools.

In this repository, the following  projects are present:

- [yasper-core](./yasper-core/Readme.md), which contains the interfaces and abstractions required to develop your RSP engine.
- [yasper-simple](./yasper-simple/Readme.md), which is an reference implementation that aims at showing yasper-core's usage by providing it.polimi.deib.rsp.test.examples.
- [yasper-esper](./yasper-esper/Readme.md), which is an implementation that uses [Esper]() as a windowing engine and relies on the following modules    
            - [Jasper](./yasper-esper/jasper/Readme.md) (AKA CSPARQL Engine 2.0): this RSP engine uses Apache Jena to execute SPARQL-like queries over RDF Streams 
            - [Seraph](./yasper-esper/seraph/Readme.md): this RSP engine uses Neo4J to execute Cypher-like queries over RDF Streams *Experimental*
- [yasper-calcite](./yasper-calcite/Readme.me): which uses Apache Calcite and Ontopic to evaluate SPARQL-like queries over virtual RDF Streams.

Futher adoption of YASPER will be listed below. A on-going [documentation](https://github.com/riccardotommasini/yasper/wiki) is also available. 

YASPER is an open and ongoing project. Welcome adoption as well as suggestion or request.

## Install Using Maven

```xml
 <repositories>
   <repository>
    <id>jitpack.io</id>
       <url>https://jitpack.io</url>
   </repository>

 </repositories>
<dependency>
   <groupId>com.github.riccardotommasini.yasper</groupId>
     <artifactId>yasper-core</artifactId>
       <version>${tag}</version>
 </dependency>
```

## Cite 

[![DOI](https://zenodo.org/badge/64671163.svg)](https://zenodo.org/badge/latestdoi/64671163)

## References

[1] [http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf](http://jeanpi.org/wp/media/rspql_ijswis_dellaglio_2015.pdf)
