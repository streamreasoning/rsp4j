package org.streamreasoning.rsp4j.cqels;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.tdb.store.NodeId;
import org.apache.commons.rdf.api.Graph;
import org.deri.cqels.data.Mapping;
import org.deri.cqels.engine.*;
import org.deri.cqels.lang.cqels.ParserCQELS;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.engine.features.StreamRegistrationFeature;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;

import java.io.File;
import java.util.Iterator;
import java.util.List;


public class CQELSEngineRSP4J implements  StreamRegistrationFeature<DataStream<Graph>, DataStream<Graph>> {

    private final ExecContext execContext;
    private ContinuousQuery continuousQuery;
    private DataStream<Binding> outputSelect;
    private DataStream<Graph> outputConstruct;

    public CQELSEngineRSP4J(String path, boolean cleanDataset) {
        File file = new File(path);
        file.mkdir();
        this.execContext = new ExecContext(path, cleanDataset);
    }
    public CQELSEngineRSP4J(){
        this("/tmp/cqels/",true);
    }
    public ContinuousQueryExecution<Graph,Binding,Binding,Binding> parseSelect(ContinuousQuery<Graph,Binding,Binding,Binding> continuousQuery) {
        this.continuousQuery = continuousQuery;
            CQELSSelectQueryRSP4j cqelsSelectQueryRSP4j = (CQELSSelectQueryRSP4j)continuousQuery;
            ContinuousSelect continuousSelect = execContext.policy().registerSelectQuery(cqelsSelectQueryRSP4j.getQuery());
            ContinuousListener myListener = new WrappedSelectListerner(outputSelect);
            continuousSelect.register(myListener);
            return  new CQELSSelectQueryExecutionRSP4J(continuousQuery, outputSelect);

    }
    public ContinuousQueryExecution<Graph,Binding,Binding,Graph> parseConstruct(ContinuousQuery<Graph,Binding,Binding,Graph> continuousQuery) {
        this.continuousQuery = continuousQuery;

        CQELSConstructQueryRSP4j cqelsConstructQueryRSP4j = (CQELSConstructQueryRSP4j)continuousQuery;
        ContinuousConstruct continousConstruct = execContext.policy().registerConstructQuery(cqelsConstructQueryRSP4j.getQuery());
        ConstructListener constructListener = new WrappedConstructListener(this.execContext, outputConstruct);
        continousConstruct.register(constructListener);
        return new CQELSConstructQueryExecutionRSP4J(continuousQuery, outputConstruct);
    }

    @Override
    public DataStream<Graph> register(DataStream<Graph> stream) {
        String streamUrl = stream.getName();
        stream.addConsumer((el, ts) -> el.stream().forEach(triple->execContext.engine().send(Node.createURI(streamUrl),
                ConvertionUtils.commonsToJena(triple))));
        return stream;
    }

    public DataStream<Binding> setSelectOutput(DataStream<Binding> outputStream){
        this.outputSelect = outputStream;
        return outputSelect;
    }
    public DataStream<Graph> setConstructOutput(DataStream<Graph> outputStream){
        this.outputConstruct = outputStream;
        return outputConstruct;
    }

    public ContinuousQuery<Graph,Binding,Binding,Binding> parseCQELSSelect(String queryStr) {
        Query query = new Query();
        ParserCQELS parser=new ParserCQELS();
        parser.parse(query, queryStr);
        return new CQELSSelectQueryRSP4j(query);
    }
    public ContinuousQuery<Graph,Binding,Binding,Graph> parseCQELSConstruct(String queryStr) {
        Query query = new Query();
        ParserCQELS parser=new ParserCQELS();
        parser.parse(query, queryStr);
        return new CQELSConstructQueryRSP4j(query);
    }
//    public ContinuousQuery<Graph,Binding,Binding,Binding> parseRSPQLSelect(String queryStr) {
//        String csparqlQuery = dialectConverter.convertToDialectFromRSPQLSyntax(queryStr, RSPDialect.CQELS);
//        System.out.println(csparqlQuery);
//        return this.parseCQELSSelect(csparqlQuery);
//    }
//    public ContinuousQuery<Graph,Binding,Binding,Graph> parseRSPQLConstruct(String queryStr) {
//        String csparqlQuery = dialectConverter.convertToDialectFromRSPQLSyntax(queryStr, RSPDialect.CQELS);
//        return this.parseCQELSConstruct(csparqlQuery);
//    }


    private class WrappedSelectListerner implements ContinuousListener {
        private final DataStream<Binding> datastream;

        public WrappedSelectListerner(DataStream<Binding> dataStream){
            this.datastream = dataStream;
        }

        @Override
        public void update(Mapping mapping) {
            BindingImpl b = new BindingImpl();

            datastream.put(decodeBinding(mapping),System.nanoTime());

        }
    }
    private class WrappedConstructListener extends ConstructListener {


        private final DataStream<Graph> outputStream;

        public WrappedConstructListener(ExecContext context, DataStream<Graph> outputStream) {
            super(context, outputStream.getName());
            this.outputStream = outputStream;
        }

        @Override
        public void update(List<Triple> graph) {
            Graph commonsGraph = RDFUtils.createGraph();
            graph.forEach(t->commonsGraph.add(ConvertionUtils.jenaToCommons(t)));
            outputStream.put(commonsGraph,System.currentTimeMillis());
        }
    }

    public Node decode(long nodeID){
        return execContext.dictionary().getNodeForNodeId(NodeId.create(nodeID));
    }
    public Binding decodeBinding(Mapping mapping){
        Iterator<Var> vars = mapping.vars();
        Binding b = new BindingImpl();
        while(vars.hasNext()){
            Var var = vars.next();
            long encoded = mapping.get(var);
            Node decodedNode = execContext.dictionary().getNodeForNodeId(NodeId.create(encoded));
            b.add(new VarImpl(var.getVarName()),RDFUtils.createIRI(decodedNode.toString()));
        }
        return b;
    }
}
