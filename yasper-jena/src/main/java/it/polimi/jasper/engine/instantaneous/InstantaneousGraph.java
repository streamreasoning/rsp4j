package it.polimi.jasper.engine.instantaneous;

import it.polimi.rspql.instantaneous.Instantaneous;
import lombok.AllArgsConstructor;
import org.apache.jena.graph.*;
import org.apache.jena.shared.AddDeniedException;
import org.apache.jena.shared.DeleteDeniedException;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.util.iterator.ExtendedIterator;

@AllArgsConstructor
public class InstantaneousGraph implements Instantaneous, Graph {

    private long timestamp;
    private Graph graph;

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(long t) {
        this.timestamp = t;
    }

    @Override
    public boolean dependsOn(Graph other) {
        return graph.dependsOn(graph);
    }

    @Override
    public TransactionHandler getTransactionHandler() {
        return graph.getTransactionHandler();
    }

    @Override
    public Capabilities getCapabilities() {
        return graph.getCapabilities();
    }

    @Override
    public GraphEventManager getEventManager() {
        return graph.getEventManager();
    }

    @Override
    public GraphStatisticsHandler getStatisticsHandler() {
        return graph.getStatisticsHandler();
    }

    @Override
    public PrefixMapping getPrefixMapping() {
        return graph.getPrefixMapping();
    }

    @Override
    public void add(Triple t) throws AddDeniedException {
        //Immutable
    }

    @Override
    public void delete(Triple t) throws DeleteDeniedException {
        //Immutable
    }

    @Override
    public ExtendedIterator<Triple> find(Triple m) {
        return graph.find(m);
    }

    @Override
    public ExtendedIterator<Triple> find(Node s, Node p, Node o) {
        return graph.find(s, p, o);
    }

    @Override
    public boolean isIsomorphicWith(Graph g) {
        return graph.isIsomorphicWith(g);
    }

    @Override
    public boolean contains(Node s, Node p, Node o) {
        return graph.contains(s, p, o);
    }

    @Override
    public boolean contains(Triple t) {
        return graph.contains(t);
    }

    @Override
    public void clear() {
        //Immutable
    }

    @Override
    public void remove(Node s, Node p, Node o) {
        //Immutable
    }

    @Override
    public void close() {
        graph.close();
    }

    @Override
    public boolean isEmpty() {
        return graph.isEmpty();
    }

    @Override
    public int size() {
        return graph.size();
    }

    @Override
    public boolean isClosed() {
        return graph.isClosed();
    }
}
