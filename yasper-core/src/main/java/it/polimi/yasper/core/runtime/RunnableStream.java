package it.polimi.yasper.core.spe.stream;

import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.Observable;
import java.util.Observer;


@Log4j
public class RunnableStream extends Observable implements Runnable, Stream {

    public RunnableStream(String iri) {
        this.iri = iri;
    }

    private String iri;
    RDF rdf = new SimpleRDF();

    public void run() {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(1000);
                log.info("Iteration [" + i + "]");
                setChanged();
                notifyObservers(new Elem(i, "Element"));
                i += 1000;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getURI() {
        return iri;
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        this.addObserver((Observer) windowAssigner);
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
