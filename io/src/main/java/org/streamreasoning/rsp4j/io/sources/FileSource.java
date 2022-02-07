package org.streamreasoning.rsp4j.io.sources;

import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Source that reads data from file, line by line. Uses a ParsingStrategy to convert the strings to object of type T.
 *
 * @param <T> resulting objects after parsing
 */
public class FileSource<T> extends DataStreamImpl<T> {

    private final String filePath;
    private final long timeOut;
    private final ParsingStrategy<T> parsingStrategy;


    /**
     * Creates a new File sources that reads one line from a file in filePath and waits timeout time between lines.
     * The parsing strategy allows to convert the readed strings to objects of type T
     *
     * @param filePath        path of the file
     * @param timeOut         timeout between reading lines (in milliseconds)
     * @param parsingStrategy parsing strategy used for converting the strings to objects of type T
     */
    public FileSource(String filePath, long timeOut, ParsingStrategy<T> parsingStrategy) {
        super(filePath);
        this.filePath = filePath;
        this.timeOut = timeOut;
        this.parsingStrategy = parsingStrategy;
    }
    public FileSource(String filePath, String streamURL, long timeOut, ParsingStrategy<T> parsingStrategy) {
        super(streamURL);
        this.filePath = filePath;
        this.timeOut = timeOut;
        this.parsingStrategy = parsingStrategy;
    }
    /**
     * Creates a new File sources that reads one line from a file in filePath and waits timeout time between lines.
     * No parsing strategy is used and the stream will return the read input type (Strings)
     *
     * @param filePath        path of the file
     * @param timeOut         timeout between reading lines (in milliseconds)
     */
    public FileSource(String filePath, long timeOut) {
        super(filePath);
        this.filePath = filePath;
        this.timeOut = timeOut;
        this.parsingStrategy = e-> (T) e;
    }

    public FileSource(String filePath, String streamURL, long timeOut) {
        super(streamURL);
        this.filePath = filePath;
        this.timeOut = timeOut;
        this.parsingStrategy = e-> (T) e;
    }

    /**
     * Starts streaming the read file data into the webstream in a new thread
     */
    public void stream() {
        Runnable runnable = () -> {
            try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
                String line;

                while ((line = br.readLine()) != null) {

                    // parse the input
                    ParsingResult<T> parsingResult = parsingStrategy.parseAndAddTime(line);
                    // send to cosumers
                    this.put(parsingResult.getResult(), parsingResult.getTimeStamp());
                    try {
                        Thread.sleep(this.timeOut);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


}
