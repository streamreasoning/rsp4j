package org.streamreasoning.rsp4j.mapping;

import com.taxonic.carml.engine.rdf.RdfRmlMapper;
import com.taxonic.carml.logicalsourceresolver.CsvResolver;
import com.taxonic.carml.logicalsourceresolver.JsonPathResolver;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import com.taxonic.carml.vocab.Rdf;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelCollector;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CARMLCSVMapper implements Function<String,String> {
    private final RdfRmlMapper mapper;
    private final String streamURI;
    private String csvHeader = null;

    public CARMLCSVMapper(String rmlMapping, String streamURI) {
        RmlMappingLoader loader = RmlMappingLoader.build();
        InputStream rmlStream = new ByteArrayInputStream(rmlMapping.getBytes());

        Set<TriplesMap> mapping = loader.load(RDFFormat.TURTLE, rmlStream);
        this.streamURI= streamURI;
        this.mapper = RdfRmlMapper.builder()
                .triplesMaps(mapping)
                .setLogicalSourceResolver(Rdf.Ql.Csv, CsvResolver::getInstance)
                .build();
    }
    @Override
    public String apply(String event) {
        if(csvHeader == null){
            csvHeader = event;
        }else{
            event = csvHeader + "\n" + event;
        }
        InputStream targetStream = new ByteArrayInputStream(event.getBytes());
        Model result = mapper.map(Map.of(this.streamURI, targetStream)).collect(ModelCollector.toTreeModel())
                .block();
        String resultString = result.stream().map(e->{
                    if(e.getObject().isIRI()){
                        return String.format("<%s> <%s> <%s> .",e.getSubject().toString(),
                                e.getPredicate().toString(),e.getObject().stringValue());
                    }
                    else{
                        return String.format("<%s> <%s> %s .",e.getSubject().toString(),
                                e.getPredicate().toString(),e.getObject().toString());
                    }
                }
        ).collect(Collectors.joining( "\n" ));

        return resultString;
    }
}