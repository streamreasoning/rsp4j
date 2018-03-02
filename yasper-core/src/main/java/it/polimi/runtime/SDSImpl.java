package it.polimi.runtime;

import it.polimi.rspql.SDS;
import lombok.AllArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class SDSImpl implements SDS {

    private Set<Graph> dg;
    private Map<IRI, Graph> ng;

    private Set<DefaultStreamView> ds;
    private Map<IRI, NamedStreamView> ns;


    @Override
    public void beforeEval() {

    }

    @Override
    public void afterEval() {

    }
}
