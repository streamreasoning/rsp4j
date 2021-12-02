package org.streamreasoning.rsp4j.reasoning.datalog;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;

import java.util.*;

public class DatalogProgram {

    private final List<ReasonerTriple> facts;
    private final List<Rule> rules;
    private int factProcessIndex = 0;
    private long counter=0;

    public DatalogProgram(){
        this.facts = new ArrayList<>();
        this.rules = new ArrayList<>();
    }
    public void addFact(ReasonerTriple t) {
        facts.add(t);
    }
    public void addFact(Triple t) {
        facts.add(new ReasonerTriple(new TermImpl(t.getSubject()),new TermImpl(t.getPredicate()),new TermImpl(t.getObject())));
    }
    public void addFacts(Graph g) {
        g.stream().forEach(t->addFact(t));
    }
    public int getFactSize() {
        return facts.size();
    }

    public void addRule(Rule r) {
        rules.add(r);
    }

    public int getRuleSize() {
        return rules.size();
    }

    public void materialize() {
        factProcessIndex = 0;
        while(factProcessIndex < facts.size()){
            ReasonerTriple evaluateFact = facts.get(factProcessIndex);

            List<Rule> matchingRules = matchFactAgainstRules(evaluateFact);
            evaluateMatchingRules(matchingRules,evaluateFact);
            factProcessIndex++;

        }
    }

    private void evaluateMatchingRules(List<Rule> matchingRules, ReasonerTriple evaluateFact) {
        for(Rule r: matchingRules){
            Rule substitutedRule = r.substitute(evaluateFact);
            Set<ReasonerTriple> inferedTriples = queryBodySubstitutedRule(substitutedRule);
            for(ReasonerTriple inferedTriple: inferedTriples){
                facts.add(inferedTriple);
            }

        }
    }
    private Set<ReasonerTriple> backwardEvaluateMatchingRules(List<Rule> matchingRules, ReasonerTriple evaluateFact) {
        Set<ReasonerTriple> toBeEvaluatedTriples = new HashSet<>();
        for(Rule r: matchingRules){
            Rule substitutedRule = r.backwardSubstitute(evaluateFact);
            Set<ReasonerTriple> possibleTriples = queryBodySubstitutedRuleBackward(substitutedRule);
            if(possibleTriples.isEmpty()){
                for(ReasonerTriple bodyTriple:substitutedRule.getBody()){
                    List<Rule> tempRules = matchFactAgainstRuleHeads(bodyTriple);
                    Set<ReasonerTriple> subTripleResult = backwardEvaluateMatchingRules(tempRules,bodyTriple);
                    // all triple patterns of the body should match
                    if(subTripleResult.isEmpty()){
                        return Collections.emptySet();
                    }else {
                        toBeEvaluatedTriples.addAll(subTripleResult);
                    }
                }
            }else {
                toBeEvaluatedTriples.addAll(possibleTriples);
            }

        }
        return toBeEvaluatedTriples;
    }
    private Set<ReasonerTriple> queryBodySubstitutedRule(Rule substitutedRule) {
//        Map<VarImpl,TermImpl> subs = new HashMap<>();
//        for(Triple bodyTriple: substitutedRule.getBody()){
//            boolean found = false;
//            for(int factIndex = 0 ; factIndex <= factProcessIndex ; factIndex++){
//                Triple factTriple = facts.get(factIndex);
//                if(bodyTriple.match(factTriple)){
//                    Map<VarImpl, TermImpl> possibleBinding = bodyTriple.extractSubstitutionVar(factTriple);
//                    if(compatibleBindinbs(subs,possibleBinding)) {
//                        found = true;
//                        subs.putAll(possibleBinding);
//                    }
//                }
//            }
//            if(!found){
//                return Optional.empty();
//            }
//        }
        Optional<Set<Binding>> bindings = query(substitutedRule.getBody().toArray(new ReasonerTriple[0]));

        Set<ReasonerTriple> newHeads = new HashSet<>();
        if(bindings.isPresent()) {
            for (Binding b : bindings.get()) {
                newHeads.add(TripleUtils.substituteBody(substitutedRule.getHead(),((BindingImpl)b).getInternals()));
            }
            if (bindings.get().isEmpty() && TripleUtils.containsNoVars(substitutedRule.getHead())) {
                newHeads.add(substitutedRule.getHead());
            }
        }
        return newHeads;

    }
    private Set<ReasonerTriple> queryBodySubstitutedRuleBackward(Rule substitutedRule) {
        Optional<Set<Binding>> bindings = queryAllFacts(substitutedRule.getBody().toArray(new ReasonerTriple[0]));

        Set<ReasonerTriple> newBodies = new HashSet<>();
        if(bindings.isPresent()) {
            for(ReasonerTriple bodyTriple: substitutedRule.getBody()){
                for (Binding b : bindings.get()) {
                    newBodies.add(TripleUtils.substituteBody(bodyTriple,((BindingImpl)b).getInternals()));
                }
            }

        }
        return newBodies;

    }

    private boolean compatibleBindinbs(Map<VarImpl, TermImpl> subs, Map<VarImpl, TermImpl> possibleBinding) {
        for(Map.Entry<VarImpl,TermImpl> entry: possibleBinding.entrySet()){
            if(subs.containsKey(entry.getKey()) && !subs.get(entry.getKey()).equals(entry.getValue())){
                return false;
            }
        }
        return true;
    }


    private List<Rule> matchFactAgainstRules(ReasonerTriple evaluateFact) {
        List<Rule> candiateRules = new ArrayList<>();
        for(Rule candidateRule: rules){
            if(candidateRule.factMatchesBody(evaluateFact)){
                candiateRules.add(candidateRule);
            }
        }
        return candiateRules;
    }
    private List<Rule> matchFactAgainstRuleHeads(ReasonerTriple evaluateFact) {
        List<Rule> candiateRules = new ArrayList<>();
        for(Rule candidateRule: rules){
            if(candidateRule.factMatchesHead(evaluateFact)){
                candiateRules.add(candidateRule);
            }
        }
        return candiateRules;
    }

    public List<ReasonerTriple> getFacts() {
        return facts;
    }
    public Optional<Set<Binding>> queryAllFacts(ReasonerTriple... triple){
        factProcessIndex = facts.size()-1;
        return query(triple);
    }
    public Optional<Set<Binding>> query(ReasonerTriple... triple) {
        Set<Binding> results = new HashSet<>();
        boolean matched = true;
        for(ReasonerTriple tp: Arrays.asList(triple)){
            Optional<Set<Binding>> bindings = evaluateTriplePattern(tp);
            matched &= bindings.isPresent();
            if(bindings.isPresent()) {
                if (results.isEmpty()) {
                    results.addAll(bindings.get());
                } else {
                    Set<Binding> newResults = new HashSet<>();
                    for (Binding existingBinding : results) {
                        for (Binding newBinding : bindings.get()) {
                            if (checkCompatibility(existingBinding,newBinding)) {
                                Binding union = unionOf(existingBinding,newBinding);
                                newResults.add(union);
                            }
                        }
                    }
                    results = newResults;
                }
            }else {
                break;
            }
        }
        return matched? Optional.of(results):Optional.empty();
    }

    private Binding unionOf(Binding b1, Binding b2){
        Binding union = new BindingImpl(((BindingImpl)b1).getInternals());
        for(Var var: b2.variables()){
            if(!((BindingImpl)b1).getInternals().containsKey(var) ){
                union.add(var, b2.value(var));
            }
        }
        return union;
    }
    private boolean checkCompatibility(Binding b1, Binding b2){
        for(Var var: b1.variables()){
            if(((BindingImpl)b2).getInternals().containsKey(var) && !((BindingImpl)b2).getInternals().get(var).equals(b1.value(var))){
                return false;
            }
        }
        return true;
    }

    private Optional<Set<Binding>> evaluateTriplePattern(ReasonerTriple tp) {
        Set<Binding> results = new HashSet<>();
        boolean foundMatch = false;
        for(int factIndex = 0 ; factIndex <= factProcessIndex ; factIndex++){
            ReasonerTriple factTriple = facts.get(factIndex);
            if(TripleUtils.matchTriples(tp,factTriple)){
                foundMatch = true;
                Map<Var, RDFTerm> possibleBinding = TripleUtils.extractSubstitutionVar(tp,factTriple);
                Binding b = new BindingImpl(possibleBinding);
                results.add(b);
            }
        }
        return foundMatch? Optional.of(results): Optional.empty();
    }

    public boolean backward(ReasonerTriple evaluateFact) {
        List<Rule> matchingRules = matchFactAgainstRuleHeads(evaluateFact);
        Set<ReasonerTriple> backwardInferredTriples = backwardEvaluateMatchingRules(matchingRules,evaluateFact);

        return !backwardInferredTriples.isEmpty();
    }

    public Rule renameFreeVariables(Rule r) {
        Set<Var> freeVars = r.getFreeVars();
        Map<Var,VarOrTerm> varSubstitutation = new HashMap<>();
        for(Var freeVar: freeVars){
            varSubstitutation.put(freeVar, generateUniqueVar());
        }
        Rule substitutedRule = r.substituteVars(varSubstitutation);
        return substitutedRule;
    }

    private VarImpl generateUniqueVar() {
        return new VarImpl("?new_" + counter++);
    }

    public Rule alignHeadVariableNames(ReasonerTriple toRewriteTo, Rule r) {
        Map<Var,VarOrTerm> varSubstitution = new HashMap<>();
        ReasonerTriple head = r.getHead();
        if(head.getSubject().isVariable() && toRewriteTo.getSubject().isVariable()){
            varSubstitution.put((VarImpl)head.getSubject(),(VarImpl)toRewriteTo.getSubject());
        }
        if(head.getProperty().isVariable() && toRewriteTo.getProperty().isVariable()){
            varSubstitution.put((VarImpl)head.getProperty(),(VarImpl)toRewriteTo.getProperty());
        }
        if(head.getObject().isVariable() && toRewriteTo.getObject().isVariable()){
            varSubstitution.put((VarImpl)head.getObject(),(VarImpl)toRewriteTo.getObject());
        }
        return r.substituteVars(varSubstitution);
    }

    public void reset(){
        this.counter = 0;
        this.factProcessIndex = 0;
        this.facts.clear();
    }


}
