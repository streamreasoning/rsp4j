package org.streamreasoning.rsp4j.yasper.sds;

import org.apache.commons.rdf.api.Graph;
import org.apache.log4j.Logger;
import org.streamreasoning.rsp4j.api.sds.DataSet;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class DataSetImpl implements DataSet<Graph> {
    private final String name;
    private final String path;
    private ParsingStrategy<Graph> parsingStrategy;
    private Collection<Graph> parsed;
    private static final Logger log = Logger.getLogger(DataSetImpl.class);
    public DataSetImpl(String name, String path, RDFBase rdfBase) {
        this.name = name;
        this.path = path;
        this.parsingStrategy = new JenaRDFCommonsParsingStrategy(rdfBase);
    }
    public void setParsingStrategy(ParsingStrategy<Graph> parsingStrategy){
        this.parsingStrategy = parsingStrategy;
    }

    private Collection<Graph> fetchData(){
        if (path != null) {
          String fromFileString = readLineByLine(this.path);
          Graph parsed = parsingStrategy.parse(fromFileString);
          return Collections.singleton(parsed);
        } else {
            log.error("No path found for loading Data Set " + name);
            return Collections.emptySet();
        }
    }
    @Override
    public Collection<Graph> getContent() {
        if(parsed==null){
            parsed = fetchData();
        }
        return parsed;
    }

    @Override
    public String getName() {
        return name;
    }

    private static String readLineByLine(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
		catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
