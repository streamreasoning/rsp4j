package neo4j.poc;


import it.polimi.jasper.streams.EPLStream;
import it.polimi.yasper.core.stream.web.WebStreamImpl;
import lombok.SneakyThrows;
import neo4j.PGraph;
import neo4j.poc.data.PGraphImpl;

public class PGraphStream extends WebStreamImpl implements Runnable {
    private EPLStream<PGraph> stream;
    private PGraph pgrah;

    public PGraphStream(String stream_uri, PGraph pgrah) {
        super(stream_uri);
        this.pgrah = pgrah;
    }
    @SneakyThrows
    @Override
    public void run() {
        while (true){
            stream.put(new PGraphImpl(),System.currentTimeMillis());
            Thread.sleep(5000);
        }
    }
    public void setWritable(EPLStream<PGraph> register) {
        this.stream = register;
    }
}
