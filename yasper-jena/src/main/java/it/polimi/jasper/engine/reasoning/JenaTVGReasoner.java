package it.polimi.jasper.engine.reasoning;

import it.polimi.jasper.engine.instantaneous.InstantaneousGraph;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.reasoner.Reasoner;

/**
 * Created by riccardo on 06/07/2017.
 */
public interface JenaTVGReasoner extends TVGReasoner<InstantaneousInfGraph, JenaGraph>, Reasoner {

}
