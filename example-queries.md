# RSP-QL Example Queries

## Background data
The following data exists at the URI ```http://temp-data.org#```:
```
@prefix prov: <http://www.w3.org/ns/prov#> .
@prefix dbp: <http://dbpedia.org/resource/> .
@prefix dbo: <http://dbpedia.org/ontology/> .
@prefix loc: <http://example.org/local/vocabulary/> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .

dbp:Berlin a dbo:City .
dbp:London a dbo:City .

# more to come...

```

### Streaming data
The following stream is available as ```http://sensor-stream.org/```

```
@prefix prov: <http://www.w3.org/ns/prov#> .
@prefix dbp: <http://dbpedia.org/resource/> .
@prefix loc: <http://example.org/local/vocabulary/> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .

_:b0  prov:generatedAtTime "2018-01-01T01:00:00Z"^^xsd:dateTimeStamp .
_:b0 {
    dbp:Berlin loc:hasPointTempC "12.5"^^xsd:decimal .
}

_:b1  prov:generatedAtTime "2018-01-01T01:01:00Z"^^xsd:dateTimeStamp .
_:b1 {
    dbp:Berlin loc:hasPointTempC "12.5"^^xsd:decimal .
}
	
_:b2  prov:generatedAtTime "2018-01-01T01:02:00Z"^^xsd:dateTimeStamp .
_:b2 {
    dbp:Berlin loc:hasPointTempC "12.0"^^xsd:decimal .
}
	
_:b3  prov:generatedAtTime "2018-01-01T01:03:00Z"^^xsd:dateTimeStamp .
_:b3 {
    dbp:London loc:hasPointTempC "11.5"^^xsd:decimal .
}
	
_:b4  prov:generatedAtTime "2018-01-01T01:04:00Z"^^xsd:dateTimeStamp .
_:b4 {
    dbp:Berlin loc:hasPointTempC "11.0"^^xsd:decimal .
}
	
_:b5  prov:generatedAtTime "2018-01-01T01:05:00Z"^^xsd:dateTimeStamp .
_:b5 {
    dbp:London loc:hasPointTempC "10.5"^^xsd:decimal .
}
	
_:b6  prov:generatedAtTime "2018-01-01T01:06:00Z"^^xsd:dateTimeStamp .
_:b6 {
    dbp:Berlin loc:hasPointTempC "10.0"^^xsd:decimal .
}
	
_:b7  prov:generatedAtTime "2018-01-01T01:08:00Z"^^xsd:dateTimeStamp .
_:b7 {
    dbp:Berlin loc:hasPointTempC "9.0"^^xsd:decimal .
}
	
_:b8  prov:generatedAtTime "2018-01-01T01:10:00Z"^^xsd:dateTimeStamp .
_:b8 {
    dbp:Berlin loc:hasPointTempC "8.5"^^xsd:decimal .
}
```

## Queries

### Query 1
Generate a stream including all events reporting temperatures below 10 C.
```
PREFIX prov: <http://www.w3.org/ns/prov#>
PREFIX dbp: <http://dbpedia.org/resource/>
PREFIX loc: <http://example.org/local/vocabulary/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

REGISTER ISTREAM <stream-below-10-degrees> AS
CONSTRUCT {
    _:b prov:generatedAtTime ?time .
    GRAPH _:b {
        ?city loc:hasPointTempC ?temp .
    }
}
FROM NAMED WINDOW <w> ON <http://sensor-stream.org/> [RANGE PT10S STEP PT1S]
WHERE {
   WINDOW <w> {
      ?city loc:hasPointTempC ?temp .
   }
   FILTER(?temp < 10)
   BIND(NOW() AS ?time)
}
```

### Expected result
| Timestamp            | Results       |
| -------------------- |:-------------|
| 2018-01-01T01:00:01Z | no result |
| 2018-01-01T01:00:02Z | no result |
| 2018-01-01T01:00:03Z | no result |
| 2018-01-01T01:00:04Z | no result |
| 2018-01-01T01:00:05Z | no result |
| 2018-01-01T01:00:06Z | no result |
| 2018-01-01T01:00:07Z | no result |
| 2018-01-01T01:00:08Z | ```_:b1 prov:generatedAtTime "2018-01-01T01:08:00Z"^^xsd:dateTimeStamp . _:b1 { dbp:Berlin loc:hasPointTempC "9.0"^^xsd:decimal } ```|
| 2018-01-01T01:00:09Z | no result |
| 2018-01-01T01:00:10Z | ```_:b2 prov:generatedAtTime "2018-01-01T10:10:00Z"^^xsd:dateTimeStamp . _:b2 { dbp:Berlin loc:hasPointTempC "8.5"^^xsd:decimal } ``` |
| 2018-01-01T01:00:11Z | no result |

### Discussion
Robin: Can we somehow access the generation time reported in the original events? Since all streamed graphs are placed in the same
default graph this does not seem feasible. Instead it would have to be included within each individual event.

