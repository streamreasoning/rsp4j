package org.streamreasoning.rsp4j.wspbook.wildstreams.dbplutils;

import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class DBPLDataFetcher {

  private final BDPLStreamType streamType;
  private final long pollingDelay;
  private static final String changesURL =
      "https://downloads.dbpedia.org/live/changesets/";
    private final int startingYear;
    private final DataStreamImpl<Graph> stream;

    public enum BDPLStreamType {
    ADDED(".added.nt.gz"),
    REINSTERT(".reinserted.nt.gz"),
    REMOVE(".removed.nt.gz");
    private String streamType;

    BDPLStreamType(String t) {
      this.streamType = t;
    }

    public String getType() {
      return streamType;
    }
  }

  public DBPLDataFetcher(BDPLStreamType streamType,  long pollingDelay, int startingYear) {
    this.streamType = streamType;
    this.pollingDelay = pollingDelay;
    this.startingYear = startingYear;
    this.stream =  new DataStreamImpl<>("https://live.dbpedia.org/live/sync/changes");

  }
    public DBPLDataFetcher(BDPLStreamType streamType, long pollingDelay) {
        this(streamType,pollingDelay,2013);
    }
    public DataStreamImpl<Graph> getStream(){
        return stream;
    }
    public void start(){
        Runnable runnable = () -> {
            try {
                this.replay(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

  private void replay(DataStreamImpl<Graph> stream) throws IOException {
        for(int year = startingYear; year<=2021; year++){
      List<String> months =
          fetchDataFromURL(changesURL + year).stream()
              .map(e->extractSubPages(e))
              .flatMap(List::stream)
              .collect(Collectors.toList());
      for(String month : months){
        List<String> days = fetchDataFromURL(changesURL + year+"/"+month).stream()
                .map(e->extractSubPages(e))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        for(String day : days){
          List<String> hours = fetchDataFromURL(changesURL + year+"/"+month+day).stream()
                  .map(e->extractSubPages(e))
                  .flatMap(List::stream)
                  .collect(Collectors.toList());
          for(String hour: hours){
            List<String> datasets = fetchDataFromURL(changesURL + year+"/"+month+day+hour).stream()
                    .map(e->extractSubPages(e))
                    .flatMap(List::stream)
                    .filter(e->e.endsWith(streamType.getType()))
                    .collect(Collectors.toList());
            for(String dataset : datasets){
              Model download = download(changesURL + year+"/"+month+day+hour+dataset);
              stream.put(download.getGraph(),System.currentTimeMillis());
                try {
                    Thread.sleep(pollingDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
          }
        }
          }
        }
  }
  private List<String> fetchDataFromURL(String fetchURL) throws IOException {
        List<String> lines = new ArrayList<>();
      URL url = new URL(fetchURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader br;
      if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
          br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      } else {
          br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
      }
      String line;
      while ((line = br.readLine()) != null) {
         lines.add(line);
      }
      return lines;
  }

  private Model download(String fileURL) {
    try {

      URL file = new URL(fileURL);

      System.out.println("[" + System.currentTimeMillis() + "]" + fileURL);

      URLConnection urlConnection = file.openConnection();
      InputStream inputStream = urlConnection.getInputStream();
      BufferedInputStream tosave = new BufferedInputStream(inputStream);
      //        save(finalLine, new GZIPInputStream(tosave));

      Model nt = ModelFactory.createDefaultModel().read(new GZIPInputStream(tosave), "", "NT");
      return nt;
    } catch (FileNotFoundException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }
    return  ModelFactory.createDefaultModel();
  }
  private List<String> extractSubPages(String pageData){
      List<String> pages = new ArrayList<>();
    Pattern pattern = Pattern.compile("\">(.+?)</a>.*");
    Matcher matcher = pattern.matcher(pageData);
    while (matcher.find())
    {
      if (!matcher.group(1).equals("../")) {
        pages.add(matcher.group(1));
      }
    }
    return pages;
  }

}
