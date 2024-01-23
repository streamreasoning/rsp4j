package org.streamreasoning.rsp4j.wspbook.wildstreams.gdeltutils;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class GDELTDataFetcher {

    private static final String source_url = "http://data.gdeltproject.org/gdeltv2/masterfilelist.txt";
    private static final String get_method = "GET";
    private static String streamType ="export";
    private static long pollingDelay=10000;
    private final DataStream<String> stream;
    private Map<String,String> headers;
    public GDELTDataFetcher(String streamType, DataStream<String> stream, long pollingDelay){
        this.streamType = streamType;
        this.stream = stream;
        this.pollingDelay = pollingDelay;
        headers = new HashMap<>();
        headers.put("export","GlobalEventID\tDay\tMonthYear\tyear\tFractionDate\tActor1Code\tActor1Name\tActor1CountryCode\tActor1KnownGroupCode\tActor1EthnicCode\tActor1Religion1Code\tActor1Religion2Code\tActor1Type1Code\tActor1Type2Code\tActor1Type3Code\tActor2Code\tActor2Name\tActor2CountryCode\tActor2KnownGroupCode\tActor2EthnicCode\tActor2Religion1Code\tActor2Religion2Code\tActor2Type1Code\tActor2Type2Code\tActor2Type3Code\tIsRootEvent\tEventCode\tEventBaseCode\tEventRootCode\tQuadClass\tGoldsteinScale\tNumMentions\tNumSources\tNumArticles\tAvgTone\tActor1Geo_Type\tActor1Geo_Fullname\tActor1Geo_CountryCode\tActor1Geo_ADM1Code\tActor1Geo_ADM2Code\tActor1Geo_Lat\tActor1Geo_Long\tActor1Geo_FeatureID\tActor2Geo_Type\tActor2Geo_Fullname\tActor2Geo_CountryCode\tActor2Geo_ADM1Code\tActor2Geo_ADM2Code\tActor2Geo_Lat\tActor2Geo_Long\tActor2Geo_FeatureID\tActionGeo_Type\tActionGeo_Fullname\tActionGeo_CountryCode\tActionGeo_ADM1Code\tActionGeo_ADM2Code\tActionGeo_Lat\tActionGeo_Long\tActionGeo_FeatureID\tDATEADDED\tSOURCEURL");
        headers.put("mentions","GlobalEventID\tEventTimeDate\tMentionTimeDate\tMentionType\tMentionSourceName\tMentionIdentifier\tSentenceID\tActor1CharOffset\tActor2CharOffset\tActionCharOffset\tInRawText\tConfidence\tMentionDocLen\tMentionDocTone\tMentionDocTranslationInfo\tExtras");
        headers.put("gkg","GKGRECORDID\tDATE\tSourceCollectionIdentifier\tSourceCommonName\tDocumentIdentifier\tCounts\tV2Counts\tThemes\tV2Themes\tLocations\tV2Locations\tPersons\tV2Persons\tOrganizations\tV2Organizations\tV2Tone\tDates\tGCAM\tSharingImage\tRelatedImages\tSocialImageEmbeds\tSocialVideoEmbeds\tQuotations\tAllNames\tAmounts\tTranslationInfo\tExtras");
    }

    public void start(){
        Runnable runnable = () -> {
            try {
                this.fetchData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    /*
     * Step (1): resources have been chosen for this stream.
     * TSV data will be downloaded from GDELT server, considering the frequency
     * with which they are pushed.
     *
     */


    /*
        The txt file downloaded from GDELT server is in the form:
        <numbers> <string> http://data.gdeltproject.org/gdeltv2/<timestamp>.export.CSV.zip
        <numbers> <string> http://data.gdeltproject.org/gdeltv2/<timestamp>.mentions.CSV.zip
        <numbers> <string> http://data.gdeltproject.org/gdeltv2/<timestamp>.gkg.CSV.zip
        We are interested in the urls because they point to the CSV related to the three streams:
        queries, mentions and gkg.
    */
    private void fetchData() throws IOException {

        String oldLine = "";

        while(true) {


            URL downloadUrl = new URL(source_url);
            HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
            connection.setRequestMethod(get_method);

            // Download the txt file from GDELT servers
            BufferedReader br;
            if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }


            String line;
            while ((line = br.readLine()) != null) {
                String todownload_address = line.split(" ")[2];

                if ((todownload_address.contains(streamType)) && !(todownload_address.equals(oldLine))) {

                    oldLine = todownload_address;
                    try{
                    URL todownload_url = new URL(todownload_address);
                    HttpURLConnection connection1 = (HttpURLConnection) todownload_url.openConnection();
                    connection1.setRequestMethod(get_method);

                    ZipInputStream zis = new ZipInputStream(connection1.getInputStream());

                    ZipEntry ze = zis.getNextEntry();

                    StringBuilder s = new StringBuilder();
                    s.append(headers.get(streamType));
                    s.append("\n");
                    byte[] buffer = new byte[1024];
                    int read = 0;

                    while ((read = zis.read(buffer, 0, 1024)) >= 0) {
                        s.append(new String(buffer, 0, read));
                    }
                    s.toString().lines().forEach(l->stream.put(l,System.currentTimeMillis()));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else if (todownload_address.equals(oldLine)) System.out.println("Already downloaded this.");
                // Check for new content every <polling_delay> milliseconds.
                try {
                    Thread.sleep(pollingDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }



    public static void main(String[] args) throws IOException {
        DataStream<String> stream = new DataStreamImpl<>("GDELTStream");
        GDELTDataFetcher fetcher = new GDELTDataFetcher("export", stream, 1000);
        stream.addConsumer((e, t) -> System.out.println(e));
        fetcher.start();
    }
}
