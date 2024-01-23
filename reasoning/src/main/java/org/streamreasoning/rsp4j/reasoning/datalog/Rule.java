package org.streamreasoning.rsp4j.reasoning.datalog;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.*;
import java.util.stream.Collectors;

public class Rule {
    private final ReasonerTriple head;
    private final ArrayList<ReasonerTriple> body;

    public Rule(ReasonerTriple head, ReasonerTriple... body) {
        this.head = head;
        this.body = new ArrayList<ReasonerTriple>(Arrays.asList(body));
    }

    public boolean factMatchesBody(ReasonerTriple evaluateFact) {
        for(ReasonerTriple bodyTriple: body){
            if(TripleUtils.matchTriples(bodyTriple,evaluateFact)){
                return true;
            }
        }
        return false;
    }
    public boolean factMatchesHead(ReasonerTriple evaluateFact){
        return TripleUtils.matchTriples(head,evaluateFact);
    }



    public Rule substitute(ReasonerTriple evaluateFact) {
        List<ReasonerTriple> substitutedBody = new ArrayList<>();
        Map<Var, RDFTerm> substutitionVariables = getSubstitutionVars(evaluateFact);
        for(ReasonerTriple bodyTriple: body){
            substitutedBody.add(TripleUtils.substituteBody(bodyTriple,substutitionVariables));
        }
        return new Rule(TripleUtils.substituteBody(head,substutitionVariables),substitutedBody.toArray(new ReasonerTriple[0]));
    }

    public Rule backwardSubstitute(ReasonerTriple evaluateFact) {
        List<ReasonerTriple> substitutedBody = new ArrayList<>();
        Map<Var,RDFTerm> substutitionVariables = getHeadSubstitutionVars(evaluateFact);
        for(ReasonerTriple bodyTriple: body){
            substitutedBody.add(TripleUtils.substituteBody(bodyTriple,substutitionVariables));
        }
        return new Rule(TripleUtils.substituteBody(head,substutitionVariables),substitutedBody.toArray(new ReasonerTriple[0]));
    }

    private Map<Var, RDFTerm> getSubstitutionVars(ReasonerTriple evaluateFact) {
        for(ReasonerTriple bodyTriple: body){
            if(TripleUtils.matchTriples(bodyTriple,evaluateFact)){
                Map<Var, RDFTerm> subVars = TripleUtils.extractSubstitutionVar(bodyTriple,evaluateFact);
                return subVars;
            }
        }
        return Collections.emptyMap();
    }
    private Map<Var, RDFTerm> getHeadSubstitutionVars(ReasonerTriple evaluateFact) {
            if(TripleUtils.matchTriples(head,evaluateFact)){
                Map<Var, RDFTerm> subVars = TripleUtils.extractSubstitutionVar(head,evaluateFact);
                return subVars;
            }

        return Collections.emptyMap();
    }

    public ArrayList<ReasonerTriple> getBody() {
        return body;
    }

    public ReasonerTriple getHead() {
        return head;
    }

    public Set<Var> getFreeVars(){
        Set<Var> headVars = getTripleVars(head);
        Set<Var> bodyVars = new HashSet<>();

        for(ReasonerTriple bodyTriple:body){
            Set<Var> currentBodyVars = getTripleVars(bodyTriple);
            bodyVars.addAll(currentBodyVars);
        }
        bodyVars.removeAll(headVars);
        return bodyVars;
    }

    private Set<Var> getTripleVars(ReasonerTriple triple) {
        Set<Var> vars = new HashSet<>() ;
        if(triple.getSubject().isVariable()){
            vars.add((VarImpl)triple.getSubject());
        }
        if(triple.getProperty().isVariable()){
            vars.add((VarImpl)triple.getProperty());
        }
        if(triple.getObject().isVariable()){
            vars.add((VarImpl)triple.getObject());
        }
        return vars;
    }

    public Rule substituteVars(Map<Var, VarOrTerm> varSubstitutation) {
        List<ReasonerTriple> substitutedBodies = body.stream().map(b -> TripleUtils.substutitionVariables(b,varSubstitutation)).collect(Collectors.toList());
        return new Rule(TripleUtils.substutitionVariables(head,varSubstitutation),substitutedBodies.toArray(body.toArray(new ReasonerTriple[0])));

    }

    @Override
    public String toString() {
        return  head + "<-" + body ;
    }
}
