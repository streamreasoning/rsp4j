package simple.streaming;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.List;


@Log4j
public class RunnableStream extends RDFStream {
    RDF rdf = new SimpleRDF();
    private List<WindowAssigner> observers;
    int i = 0;
    
    public RunnableStream(String iri) {
        super(iri);
        observers = new ArrayList<>();
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {

        new Thread(() -> {
            final WindowAssigner wa = windowAssigner;
            while (true) {
                try {
                    Thread.sleep(1000);
                    log.info("Iteration [" + i + "]");
                    wa.notify(new Elem(i, "Element"));
                    i += 1000;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class Elem implements StreamElement {

        private long timestamp;
        private Triple content;

        public Elem(long i, String s) {
            timestamp = i;
            content = rdf.createTriple(rdf.createIRI("http://asubject/" + i), rdf.createIRI("http://aproperty"), rdf.createIRI("http://anobject" + i));

        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public Object getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "(" + getContent().toString() + "," + timestamp + ") " + hashCode();
        }
    }
}
