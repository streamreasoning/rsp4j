package it.polimi.rsp.yasper.csparql;

import com.hp.hpl.jena.reasoner.Reasoner;
import eu.larkc.csparql.cep.api.CepEngine;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.sparql.api.SparqlEngine;
import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.rdf.RDFStream;
import it.polimi.yasper.core.utils.QueryConfiguration;

public class CSPARQL implements RSPEngine<RdfQuadruple> {


    private CepEngine cepEngine = null;
    private SparqlEngine sparqlEngine = null;
    private Reasoner reasoner = null;


    @Override
    public boolean process(RdfQuadruple var1) {
        return false;
    }

    @Override
    public void unregister(ContinuousQuery qId) {

    }

    @Override
    public void unregister(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    @Override
    public void register(ContinuousQuery q, QueryResponseFormatter o) {

    }

    @Override
    public void register(ContinuousQueryExecution cqe, QueryResponseFormatter o) {

    }

    @Override
    public ContinuousQuery parseQuery(String input) {
        return null;
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        return null;
    }

    @Override
    public ContinuousQueryExecution register(String q, QueryConfiguration c) {
        return null;
    }

    @Override
    public void unregister(RDFStream rdfStream) {

    }

    @Override
    public RDFStream register(RDFStream rdfStream) {
        return null;
    }
}
