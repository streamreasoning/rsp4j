package org.streamreasoning.rsp4j.yasper.querying.syntax;


import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.enums.StreamOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BGP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRSPQLQuery<O> implements RSPQL<O> {

    private RelationToStreamOperator<Binding, O> r2s;
    private String id;

    private DataStream<O> outputStream;
    private List<TripleHolder> triples;

    private Map<WindowNode, DataStream<Graph>> windowMap = new HashMap<>();
    private List<String> graphURIs = new ArrayList<>();
    private List<String> namedwindowsURIs = new ArrayList<>();
    private List<String> namedGraphURIs = new ArrayList<>();
    private List<Aggregation> aggregations = new ArrayList<>();
    private StreamOperator streamOperator = StreamOperator.NONE;
    private Time time;

    public SimpleRSPQLQuery(String id, DataStream<Graph> stream, Time time, WindowNode win, VarOrTerm s, VarOrTerm p, VarOrTerm o, RelationToStreamOperator<Binding, O> r2s) {
        this.id = id;
        this.outputStream = new DataStreamImpl<O>(id);
        this.triples = new ArrayList<>();
        TripleHolder triple = new TripleHolder(s,p,o);
        triples.add(triple);
        if (win != null && stream != null) {
            windowMap.put(win, stream);
        }
        this.r2s = r2s;
        this.time = time;

    }
    public SimpleRSPQLQuery(String id, DataStream<Graph> stream, Time time, WindowNode win, List<TripleHolder> triplePatterns, RelationToStreamOperator<Binding, O> r2s) {
        this.id = id;
        this.outputStream = new DataStreamImpl<O>(id);
        this.triples = triplePatterns;

        if (win != null && stream != null) {
            windowMap.put(win, stream);
        }
        this.r2s = r2s;
        this.time = time;

    }
    public SimpleRSPQLQuery(String id) {
        this.id = id;
    }


    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        windowMap.put(wo, new DataStreamImpl<>(streamUri));
    }

    @Override
    public void setIstream() {
        streamOperator = StreamOperator.ISTREAM;
    }

    @Override
    public void setRstream() {
        streamOperator = StreamOperator.RSTREAM;
    }

    @Override
    public void setDstream() {
        streamOperator = StreamOperator.DSTREAM;
    }

    @Override
    public boolean isIstream() {
        return streamOperator.equals(StreamOperator.ISTREAM);
    }

    @Override
    public boolean isRstream() {
        return streamOperator.equals(StreamOperator.RSTREAM);
    }

    @Override
    public boolean isDstream() {
        return streamOperator.equals(StreamOperator.DSTREAM);
    }

    @Override
    public void setSelect() {

    }

    @Override
    public void setConstruct() {

    }

    @Override
    public boolean isSelectType() {
        return false;
    }

    @Override
    public boolean isConstructType() {
        return false;
    }

    @Override
    public DataStream<O> getOutputStream() {
        return outputStream;
    }

    @Override
    public void setOutputStream(String uri) {
        this.outputStream = new DataStreamImpl<>(uri);
    }

    @Override
    public String getID() {
        return id;
    }


    @Override
    public Map<WindowNode, DataStream<Graph>> getWindowMap() {
        return windowMap;
    }


    @Override
    public Time getTime() {
        return this.time;
    }

    @Override
    public RelationToRelationOperator<Graph, Binding> r2r() {
        if(triples.size()==1){
            TripleHolder triple = triples.get(0);
            return new TP(triple.s, triple.p, triple.o);
        }else{
            return createBGP();
        }

    }
    private BGP createBGP(){
        TripleHolder triple = triples.get(0);
        BGP bgp = BGP.createFrom(new TP(triple.s,triple.p,triple.o));
        for(int i = 1; i < triples.size(); i++){
            bgp.join(new TP(triples.get(i).s,triples.get(i).p,triples.get(i).o));
        }
        return bgp.create();
    }
    @Override
    public StreamToRelationOp<Graph, Graph>[] s2r() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToStreamOperator<Binding, O> r2s() {
        return new Rstream();
    }

    @Override
    public List<Aggregation> getAggregations() {
        return aggregations;
    }
}
