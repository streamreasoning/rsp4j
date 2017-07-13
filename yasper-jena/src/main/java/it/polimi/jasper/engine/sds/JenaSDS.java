package it.polimi.jasper.engine.sds;

import it.polimi.jasper.engine.instantaneous.InstantaneousModel;
import it.polimi.yasper.core.SDS;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

/**
 * Created by riccardo on 01/07/2017.
 */
public interface JenaSDS extends SDS, Dataset {

    public void addDefaultWindow(InstantaneousModel m);

    public boolean addNamedWindowStream(String w, String s, Model m);

}
