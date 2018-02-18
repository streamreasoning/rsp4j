package it.polimi.spe.stream;

import it.polimi.rspql.Stream;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;


public class RunnableStream extends Observable implements Runnable, Stream {

    final Logger log = LoggerFactory.getLogger(RunnableStream.class);

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
        return null;
    }

    @Override
    public void addObserver(WindowAssigner windowAssigner) {
        this.addObserver(windowAssigner);
    }

    public class Elem implements StreamElement {

        private long timestamp;
        private Object content;

        public Elem(long i, Object c) {
            timestamp = i;
            content = c;

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
