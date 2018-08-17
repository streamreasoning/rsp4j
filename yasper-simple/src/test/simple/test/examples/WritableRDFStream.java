package simple.test.examples;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.stream.rdf.RDFStream;

import java.util.ArrayList;
import java.util.List;

public class WritableRDFStream extends RDFStream {

    List<WindowAssigner> assigners = new ArrayList<>();

    public WritableRDFStream(String stream_uri) {
        super(stream_uri);
    }

    public void put(Elem e) {
        assigners.forEach(windowAssigner -> windowAssigner.notify(e));
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        assigners.add(windowAssigner);
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
