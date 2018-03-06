package it.polimi.jasper.engine.reasoning;

import it.polimi.yasper.simple.windowing.TimeVarying;
import org.apache.jena.reasoner.InfGraph;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface TimeVaryingInfGraph extends InfGraph, TimeVarying<InfGraph> {


}
