package org.streamreasoning.rsp4j.io.sources;

import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Source that retrieves data through SSE. Uses {@link ParsingStrategy} for creating of objects of type T.
 *
 * @param <T> generic type of objects that populate the stream.
 */
public class SSESource<T> extends DataStreamImpl<T> {

    private final String url;
    private final long timeOut;
    private final ParsingStrategy<T> parsingStrategy;
    private volatile boolean streaming = false;
    private Map<String, String> requestOption;

    /**
     * Creates a new SSESource
     *
     * @param streamURI       the uri of the SSE, this is only used for linking to the SSE internally
     * @param url             the url of the external access point
     * @param timeout         the timeout in milliseconds between pulling the access point
     * @param parsingStrategy the parsing strategy used for parsing the received strings to objects of type T
     */
    public SSESource(String streamURI, String url, long timeout, ParsingStrategy<T> parsingStrategy) {
        super(streamURI);
        this.url = url;
        this.timeOut = timeout;
        this.parsingStrategy = parsingStrategy;
        this.requestOption = new HashMap<>();

    }

    /**
     * Creates a new SSE and using the url of the access point as {@code streamURI}
     *
     * @param url             the url of the  access point, this url is used as streamURI
     * @param timeout         the timeout in milliseconds between pulling the access point
     * @param parsingStrategy the parsing strategy used for parsing the received strings to objects of type T
     */
    public SSESource(String url, long timeout, ParsingStrategy<T> parsingStrategy) {
        this(url, url, timeout, parsingStrategy);
    }
    /**
     * Creates a new SSE and using the url of the access point as {@code streamURI}. No parsing strategy is used.
     *
     * @param url             the url of the  access point, this url is used as streamURI
     * @param timeout         the timeout in milliseconds between pulling the access point
     */
    public SSESource(String url, long timeout) {
        this(url, url, timeout, e->(T)e);
    }

    /**
     * Start pulling the http access point and populating the stream.
     * The {@link ParsingStrategy} is used for converting the received strings to objects of type T
     */
    public void stream() {
        this.streaming = true;
        Runnable task = () -> {
            if (this.streaming) {
                URL urlCon;
                try {
                    urlCon = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection)urlCon.openConnection();
                    conn.setRequestMethod("GET");
                    requestOption.entrySet().stream().forEach(e->conn.setRequestProperty(e.getKey(),e.getValue()));
                    InputStream is = conn.getInputStream();

                    String result = "";
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                        while((result = br.readLine())!=null && this.streaming){
                            ParsingResult<T> parsingResult = parsingStrategy.parseAndAddTime(result);
                            this.put(parsingResult.getResult(), parsingResult.getTimeStamp());
                            Thread.sleep(this.timeOut);
                        }
                    }

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * Stops the stream
     */
    public void stop() {
        this.streaming = false;
    }

    /**
     * Add options to the HTTP request header
     * @param key       key of the request header option
     * @param value     value of the request header option
     */
    public void addRequestOptions(String key, String value){
        this.requestOption.put(key,value);
    }
}
