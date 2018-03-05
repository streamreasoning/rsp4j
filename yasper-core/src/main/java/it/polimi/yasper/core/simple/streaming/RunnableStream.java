package it.polimi.yasper.core.simple.streaming;

import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.List;


@Log4j
public class RunnableStream extends RDFStream implements Runnable, Stream {

    private List<WindowAssigner> observers;

    public RunnableStream(String iri) {
        super(iri);
        observers = new ArrayList<>();
    }

    RDF rdf = new SimpleRDF();

    public void run() {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(1000);
                log.info("Iteration [" + i + "]");
                notifyObservers(new Elem(i, "Element"));
                i += 1000;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyObservers(Elem element) {
        observers.forEach(wa -> wa.notify(element));
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        this.observers.add(windowAssigner);
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
