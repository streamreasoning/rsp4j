package org.streamreasoning.rsp4j.io.utils.parsing;

public class TimeExtractingParsingStrategy<T> implements ParsingStrategy<T>{


    private final int timeIndex;
    private final String delimiter;
    private final ParsingStrategy<T> parsingStrategy;

    public TimeExtractingParsingStrategy(int timeIndex, String delimiter, ParsingStrategy<T> parsingStrategy){
        this.timeIndex = timeIndex;
        this.delimiter = delimiter;
        this.parsingStrategy = parsingStrategy;
    }
    @Override
    public ParsingResult<T> parseAndAddTime(String parseString) {
        // split the string and extract time stamp and object
        String[] split = parseString.split(this.delimiter);
        long timeStamp = Long.parseLong(split[this.timeIndex].trim());
        int objectIndex = Math.abs(timeIndex - 1);
        String parseObjString = split[objectIndex];
        ParsingResult<T> parseResult = parsingStrategy.parseAndAddTime(parseObjString);
        parseResult.setTimeStamp(timeStamp);
        return parseResult;
    }

    @Override
    public T parse(String parseString) {
        return parsingStrategy.parse(parseString);
    }
}
