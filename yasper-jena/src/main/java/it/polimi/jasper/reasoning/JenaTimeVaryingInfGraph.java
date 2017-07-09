package it.polimi.jasper.reasoning;

import it.polimi.jasper.sds.JenaTimeVaryingGraph;
import it.polimi.yasper.core.query.TimeVaryingInfGraph;
import org.apache.jena.reasoner.InfGraph;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface JenaTimeVaryingInfGraph extends JenaTimeVaryingGraph, InfGraph, TimeVaryingInfGraph {


}
