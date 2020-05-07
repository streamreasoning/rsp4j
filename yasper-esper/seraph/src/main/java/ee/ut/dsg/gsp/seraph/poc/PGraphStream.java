package ee.ut.dsg.gsp.seraph.poc;


import ee.ut.dsg.gsp.seraph.PGraph;
import ee.ut.dsg.gsp.seraph.poc.data.PGraphImpl;
import it.polimi.jasper.streams.EPLStream;
import it.polimi.yasper.core.stream.web.WebStreamImpl;
import lombok.SneakyThrows;

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
