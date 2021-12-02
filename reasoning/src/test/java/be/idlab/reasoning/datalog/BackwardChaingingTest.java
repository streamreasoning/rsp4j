package be.idlab.reasoning.datalog;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BackwardChaingingTest {

    //@Test
    public void singleRuleEvaluation(){

        Rule r = new Rule(new Triple("?x", "a", "Person"),new Triple("?x", "a", "Student"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        Triple fact = new Triple("s", "a" , "Student");
        p.addFact(fact);
        Triple possibleInferredFact = new Triple("s", "a" , "Person");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void doubleRuleEvaluation(){

        Rule r = new Rule(new Triple("?x", "a", "Person"),new Triple("?x", "a", "Student"));
        Rule r2 = new Rule(new Triple("?x", "a", "Mammal"),new Triple("?x", "a", "Person"));

        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        Triple fact = new Triple("s", "a" , "Student");
        p.addFact(fact);
        Triple possibleInferredFact = new Triple("s", "a" , "Mammal");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void joinRuleEvaluation(){

        Rule r2 = new Rule(new Triple("?x", "a", "Teacher"),new Triple("?x", "a", "Person"), new Triple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new Triple("s2", "teaches" , "c1"));
        p.addFact(new Triple("s1", "teaches" , "c1"));
        p.addFact(new Triple("s1", "a" , "Person"));
        p.addFact(new Triple("c1", "a" , "Course"));

        Triple possibleInferredFact = new Triple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void joinDoubleRuleEvaluation(){
        Rule r = new Rule(new Triple("?x", "a", "Person"),new Triple("?x", "a", "Thinker"));

        Rule r2 = new Rule(new Triple("?x", "a", "Teacher"),new Triple("?x", "a", "Person"), new Triple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        p.addFact(new Triple("s2", "teaches" , "c1"));
        p.addFact(new Triple("s1", "teaches" , "c1"));
        p.addFact(new Triple("s1", "a" , "Thinker"));
        p.addFact(new Triple("c1", "a" , "Course"));

        Triple possibleInferredFact = new Triple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
    //@Test
    public void joinDoubleRuleEvaluationNoMatch(){
        Rule r = new Rule(new Triple("?x", "a", "Person"),new Triple("?x", "a", "Thinker"));

        Rule r2 = new Rule(new Triple("?x", "a", "Teacher"),new Triple("?x", "a", "Person"), new Triple("?x", "teachesSomething","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        p.addFact(new Triple("s2", "teaches" , "c1"));
        p.addFact(new Triple("s1", "teaches" , "c1"));
        p.addFact(new Triple("s1", "a" , "Thinker"));
        p.addFact(new Triple("c1", "a" , "Course"));

        Triple possibleInferredFact = new Triple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(false,isDerivable);
    }
    //@Test
    public void joinDoubleRuleEvaluationDifferentVarNames(){
        Rule r = new Rule(new Triple("?p", "a", "Person"),new Triple("?p", "a", "Thinker"));

        Rule r2 = new Rule(new Triple("?x", "a", "Teacher"),new Triple("?x", "a", "Person"), new Triple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        p.addFact(new Triple("s2", "teaches" , "c1"));
        p.addFact(new Triple("s1", "teaches" , "c1"));
        p.addFact(new Triple("s1", "a" , "Thinker"));
        p.addFact(new Triple("c1", "a" , "Course"));

        Triple possibleInferredFact = new Triple("s1", "a" , "Teacher");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
   // @Test
    public void doubleRuleEvaluationDifferentVarNames(){

        Rule r = new Rule(new Triple("?p", "a", "Person"),new Triple("?p", "a", "Student"));
        Rule r2 = new Rule(new Triple("?x", "a", "Mammal"),new Triple("?x", "a", "Person"));

        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        Triple fact = new Triple("s", "a" , "Student");
        p.addFact(fact);
        Triple possibleInferredFact = new Triple("s", "a" , "Mammal");
        boolean isDerivable = p.backward(possibleInferredFact);
        assertEquals(true,isDerivable);
    }
}
