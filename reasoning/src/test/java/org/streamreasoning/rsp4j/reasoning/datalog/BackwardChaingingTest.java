package org.streamreasoning.rsp4j.reasoning.datalog;

import static org.junit.Assert.assertEquals;

public class BackwardChaingingTest {

    //@Test
    public void singleRuleEvaluation(){

        Rule r = new Rule(new ReasonerTriple("?x", "a", "Person"),new ReasonerTriple("?x", "a", "Student"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        ReasonerTriple fact = new ReasonerTriple("s", "a" , "Student");
        p.addFact(fact);
        ReasonerTriple possibleInferredFact = new ReasonerTriple("s", "a" , "Person");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void doubleRuleEvaluation(){

        Rule r = new Rule(new ReasonerTriple("?x", "a", "Person"),new ReasonerTriple("?x", "a", "Student"));
        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Mammal"),new ReasonerTriple("?x", "a", "Person"));

        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        ReasonerTriple fact = new ReasonerTriple("s", "a" , "Student");
        p.addFact(fact);
        ReasonerTriple possibleInferredFact = new ReasonerTriple("s", "a" , "Mammal");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void joinRuleEvaluation(){

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Teacher"),new ReasonerTriple("?x", "a", "Person"), new ReasonerTriple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s2", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "a" , "Person"));
        p.addFact(new ReasonerTriple("c1", "a" , "Course"));

        ReasonerTriple possibleInferredFact = new ReasonerTriple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void joinDoubleRuleEvaluation(){
        Rule r = new Rule(new ReasonerTriple("?x", "a", "Person"),new ReasonerTriple("?x", "a", "Thinker"));

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Teacher"),new ReasonerTriple("?x", "a", "Person"), new ReasonerTriple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s2", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "a" , "Thinker"));
        p.addFact(new ReasonerTriple("c1", "a" , "Course"));

        ReasonerTriple possibleInferredFact = new ReasonerTriple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void joinDoubleRuleEvaluationNoMatch(){
        Rule r = new Rule(new ReasonerTriple("?x", "a", "Person"),new ReasonerTriple("?x", "a", "Thinker"));

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Teacher"),new ReasonerTriple("?x", "a", "Person"), new ReasonerTriple("?x", "teachesSomething","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s2", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "a" , "Thinker"));
        p.addFact(new ReasonerTriple("c1", "a" , "Course"));

        ReasonerTriple possibleInferredFact = new ReasonerTriple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(false,isDerivable);
    }
    //@Test
    public void joinDoubleRuleEvaluationDifferentVarNames(){
        Rule r = new Rule(new ReasonerTriple("?p", "a", "Person"),new ReasonerTriple("?p", "a", "Thinker"));

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Teacher"),new ReasonerTriple("?x", "a", "Person"), new ReasonerTriple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s2", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "a" , "Thinker"));
        p.addFact(new ReasonerTriple("c1", "a" , "Course"));

        ReasonerTriple possibleInferredFact = new ReasonerTriple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
   // @Test
    public void doubleRuleEvaluationDifferentVarNames(){

        Rule r = new Rule(new ReasonerTriple("?p", "a", "Person"),new ReasonerTriple("?p", "a", "Student"));
        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Mammal"),new ReasonerTriple("?x", "a", "Person"));

        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        ReasonerTriple fact = new ReasonerTriple("s", "a" , "Student");
        p.addFact(fact);
        ReasonerTriple possibleInferredFact = new ReasonerTriple("s", "a" , "Mammal");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
}
