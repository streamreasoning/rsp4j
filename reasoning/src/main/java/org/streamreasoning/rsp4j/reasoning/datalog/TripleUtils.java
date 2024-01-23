package org.streamreasoning.rsp4j.reasoning.datalog;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.HashMap;
import java.util.Map;

public class TripleUtils {



    public static boolean matchTriples(ReasonerTriple t1, ReasonerTriple t2) {
        if (t1.getSubject().isTerm() && !t1.getSubject().equals(t2.getSubject())) {
            return false;
        }
        if (t1.getProperty().isTerm() && !t1.getProperty().equals(t2.getProperty())) {
            return false;
        }
        if (t1.getObject().isTerm() && !t1.getObject().equals(t2.getObject())) {
            return false;
        }
        return true;
    }

    public static Map<Var, RDFTerm> extractSubstitutionVar(ReasonerTriple triple, ReasonerTriple evaluateFact) {
        Map<Var, RDFTerm> subs = new HashMap<Var, RDFTerm>();
        if (triple.getSubject().isVariable()) {
            subs.put(triple.getSubject(), evaluateFact.getSubject());
        }
        if (triple.getProperty().isVariable()) {
            subs.put(triple.getProperty(), evaluateFact.getProperty());
        }
        if (triple.getObject().isVariable()) {
            subs.put(triple.getObject(), evaluateFact.getObject());
        }
        return subs;
    }

    public static ReasonerTriple substituteBody(ReasonerTriple triple, Map<Var, RDFTerm> substutitionVariables) {
        VarOrTerm s = triple.getSubject();
        VarOrTerm p = triple.getProperty();
        VarOrTerm o = triple.getObject();
        if (substutitionVariables.containsKey(triple.getSubject())) {
            s = (VarOrTerm) substutitionVariables.get(triple.getSubject());
        }
        if (substutitionVariables.containsKey(triple.getProperty())) {
            p = (VarOrTerm) substutitionVariables.get(triple.getProperty());
        }
        if (substutitionVariables.containsKey(triple.getObject())) {
            o = (VarOrTerm) substutitionVariables.get(triple.getObject());
        }
        return new ReasonerTriple(s, p, o);
    }

    public static ReasonerTriple substutitionVariables(ReasonerTriple triple, Map<Var, VarOrTerm> substutitionVariables) {
        VarOrTerm s = triple.getSubject();
        VarOrTerm p = triple.getProperty();
        VarOrTerm o = triple.getObject();
        if (substutitionVariables.containsKey(triple.getSubject())) {
            s = substutitionVariables.get(triple.getSubject());
        }
        if (substutitionVariables.containsKey(triple.getProperty())) {
            p = substutitionVariables.get(triple.getProperty());
        }
        if (substutitionVariables.containsKey(triple.getObject())) {
            o = substutitionVariables.get(triple.getObject());
        }
        return new ReasonerTriple(s, p, o);
    }
    public static boolean containsNoVars(ReasonerTriple triple) {
        return triple.getSubject().isTerm()&&triple.getProperty().isTerm()&&triple.getObject().isTerm();
    }

}