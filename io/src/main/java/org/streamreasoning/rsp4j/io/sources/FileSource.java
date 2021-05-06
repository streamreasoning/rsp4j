package org.streamreasoning.rsp4j.io.sources;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Source that reads data from file, line by line. Uses a ParsingStrategy to convert the strings to object of type T.
 *
 * @param <T>  resulting objects after parsing
 */
public class FileSource<T> implements WebDataStream<T> {

  private final String filePath;
  private final long timeOut;
  private final ParsingStrategy<T> parsingStrategy;
  protected List<Consumer<T>> consumers = new ArrayList<>();
  protected String stream_uri;

  /**
   * Creates a new File sources that reads one line from a file in filePath and waits timeout time between lines.
   * The parsing strategy allows to convert the readed strings to objects of type T
   * @param filePath  path of the file
   * @param timeOut  timeout between reading lines
   * @param parsingStrategy  parsing strategy used for converting the strings to objects of type T
   */
  public FileSource(String filePath, long timeOut, ParsingStrategy<T> parsingStrategy) {
    this.filePath = filePath;
    this.timeOut = timeOut;
    this.parsingStrategy = parsingStrategy;
  }

  /**
   * Starts streaming the read file data into the webstream
   */
  public void stream() {

    try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
      String line;

      while ((line = br.readLine()) != null) {

        // parse the input
        ParsingResult<T> parsingResult = parsingStrategy.parse(line);
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
  }

  @Override
  public void addConsumer(Consumer<T> c) {
    consumers.add(c);
  }

  @Override
  public void put(T t, long ts) {
    consumers.forEach(graphConsumer -> graphConsumer.notify(t, ts));
  }

  @Override
  public String uri() {
    return this.stream_uri;
  }
}
