package org.streamreasoning.rsp4j.wspbook.wildstreams.gdeltutils;

import com.taxonic.carml.engine.function.FnoFunction;
import com.taxonic.carml.engine.function.FnoParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class URISplitFunction {

    private final String separator;

    public URISplitFunction(String separator) {
        this.separator = separator;
    }

    @FnoFunction("http://example.org/splitURIs")
    public List<String> split(@FnoParam("http://example.org/contentURIs") String content) {
        if (content != null)
            return Arrays.stream(content.split(separator)).collect(Collectors.toList());
        return Collections.emptyList();
    }
}