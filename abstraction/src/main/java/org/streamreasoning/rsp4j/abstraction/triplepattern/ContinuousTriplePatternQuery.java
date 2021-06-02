package org.streamreasoning.rsp4j.abstraction.triplepattern;


import org.streamreasoning.rsp4j.yasper.querying.formatter.ContinuousQueryImpl;



public class ContinuousTriplePatternQuery extends ContinuousQueryImpl {
  private String triplePattern;
  private String stream_uri;

  public ContinuousTriplePatternQuery(String id, String stream_uri,String triplePattern) {
    super(id);
    this.triplePattern = triplePattern;
    this.stream_uri = stream_uri;
  }

  public String getTriplePattern(){
    return triplePattern;
  }

  public String getStreamURI(){
    return stream_uri;
  }
}
