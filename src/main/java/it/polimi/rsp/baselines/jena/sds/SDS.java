package it.polimi.rsp.baselines.jena.sds;

import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface SDS {
    public boolean addStatementName(String c);

    public boolean addDefaultWindowStream(String uri);

    public boolean addNamedWindowStream(String w, String s, Model m);

    public Model bind(Graph g);

}
