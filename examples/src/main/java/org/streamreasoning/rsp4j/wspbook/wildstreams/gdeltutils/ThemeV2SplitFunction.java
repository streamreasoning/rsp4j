package org.streamreasoning.rsp4j.wspbook.wildstreams.gdeltutils;

import com.taxonic.carml.engine.function.FnoFunction;
import com.taxonic.carml.engine.function.FnoParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ThemeV2SplitFunction {

    private final String separator;
    private final String prefix;

    public ThemeV2SplitFunction(String separator, String prefix) {
        this.separator = separator;
        this.prefix = prefix;
    }

    @FnoFunction("http://example.org/split2")
    public List<String> split(@FnoParam("http://example.org/content2") String content) {
        if (content != null)
            return Arrays.stream(content.split(separator)).map(s -> s.split(",")[0]).map(s -> prefix + s).collect(Collectors.toList());
        return Collections.emptyList();
    }
}
