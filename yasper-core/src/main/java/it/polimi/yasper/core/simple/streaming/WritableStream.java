package it.polimi.yasper.core.simple.streaming;

import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;

import java.util.Observable;

public class WritableStream extends Observable implements Stream {

    public void put(Elem e) {
        setChanged();
        notifyObservers(e);
    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        this.addWindowAssiger(windowAssigner);
    }

    public static class Elem implements StreamElement {

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
