package org.streamreasoning.rsp4j.io.utils.parsing;


/**
 * Wrapper class that combines a parsed object with its time stamp.
 *
 * For cases where input contains both timestamp and parsing item, e.g.
 * "1590809839, <http://test/subject> <http://test/property> <http://test/subject>".
 * Not all parsers will be able to extract a timestamp, in that case current time can be assigned.
 *
 * @param <T>  the type of the parsed object
 */
public class ParsingResult<T> {

    private final T object;
    private final long timeStamp;

    /**
     * Creates new ParsingResult with custom time stamp
     * @param object  parsed object
     * @param timeStamp  extracted time stamp
     */
    public ParsingResult(T object, long timeStamp){
        this.object = object;
        this.timeStamp = timeStamp;
    }

    /**
     * Creates new ParsingResult and assigns current time as time stamp
     * @param object  parsed object
     */
    public ParsingResult(T object){
        this(object,System.currentTimeMillis());
    }

    /**
     * Returns the time stamp assigned to the parsed object
     * @return  time stamp
     */
    public long getTimeStamp(){
        return timeStamp;
    }

    /**
     * Returns the parsed object of type T
     * @return  parsed object
     */
    public T getResult(){
        return object;
    }
}
