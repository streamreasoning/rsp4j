package org.streamreasoning.rsp4j.wspbook.wildstreams.gdeltutils;

import com.taxonic.carml.engine.function.FnoFunction;
import com.taxonic.carml.engine.function.FnoParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSplitterFunction {

    private final String regex;
    private final Pattern pattern;
    private final int group;

    public RegexSplitterFunction(String regex, int group) {
        this.regex = regex;
        this.group = group;
        pattern = Pattern.compile(regex);
    }

    @FnoFunction("http://example.org/regexExtraction")
    public Long split(@FnoParam("http://example.org/extras") String extras) {
        if (extras != null) {
            Matcher matcher = pattern.matcher(extras);
            while (matcher.find()) {
                try {
                    return Long.parseLong(matcher.group(group));
                } catch (NumberFormatException | IllegalStateException e) {
                    return null;
                }
            }

        }
        return null;
    }

    public static void main(String[] args){

        String s ="<PAGE_PRECISEPUBTIMESTAMP>20190319163000</PAGE_PRECISEPUBTIMESTAMP><PAGE_ALTURL_AMP>https://www.nwitimes.com/news/national/white-officer-on-trial-in-fatal-shooting-of-antwon-rose/article_48ce497a-1e74-50c2-8c10-04dc3dbcc711.amp.html</PAGE_ALTURL_AMP>";
        RegexSplitterFunction regexSplitterFunction = new RegexSplitterFunction("(.*)<PAGE_PRECISEPUBTIMESTAMP>([0-9]+)</PAGE_PRECISEPUBTIMESTAMP>(.*)", 2);

        System.out.println(
                regexSplitterFunction.split(s));

    }
}