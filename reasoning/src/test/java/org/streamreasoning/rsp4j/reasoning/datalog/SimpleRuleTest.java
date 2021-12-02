package org.streamreasoning.rsp4j.reasoning.datalog;


import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleRuleTest {

    @Test
    public void addFactTest(){
        ReasonerTriple t = new ReasonerTriple("s", "p" , "o");
        DatalogProgram p = new DatalogProgram();
        p.addFact(t);
        assertEquals(1,p.getFactSize());
    }

    @Test
    public void addRuleTest(){
        VarOrTerm x = new VarImpl("x");
        VarOrTerm t = new TermImpl("t");
        VarOrTerm y = new VarImpl("y");
        ReasonerTriple head = new ReasonerTriple(x,t,y);
        ReasonerTriple body1 = new ReasonerTriple(x,t,y);
        ReasonerTriple body2 = new ReasonerTriple(x,t,y);
        Rule r = new Rule(head,body1, body2);
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);

        assertEquals(1,p.getRuleSize());
    }
    @Test
    public void simpleRuleEvaluation(){
        ReasonerTriple head = new ReasonerTriple("?x", "a", "Person");
        ReasonerTriple body1 = new ReasonerTriple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        ReasonerTriple fact = new ReasonerTriple("s", "a" , "Student");
        p.addFact(fact);
        p.materialize();
        assertEquals(2,p.getFactSize());
        assertEquals(new ReasonerTriple("s", "a" , "Person"),p.getFacts().get(1));
    }
    @Test
    public void doubleRuleEvaluation(){
        ReasonerTriple head = new ReasonerTriple("?x", "a", "Person");
        ReasonerTriple body1 = new ReasonerTriple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Mammal"),new ReasonerTriple("?x", "a", "Person"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        ReasonerTriple fact = new ReasonerTriple("s", "a" , "Student");
        p.addFact(fact);
        p.materialize();
        assertEquals(3,p.getFactSize());
        assertEquals(new ReasonerTriple("s", "a" , "Person"),p.getFacts().get(1));
        assertEquals(new ReasonerTriple("s", "a" , "Mammal"),p.getFacts().get(2));

    }
    @Test
    public void doubleFactEvaluation(){
        ReasonerTriple head = new ReasonerTriple("?x", "a", "Person");
        ReasonerTriple body1 = new ReasonerTriple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Mammal"),new ReasonerTriple("?x", "a", "Person"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        ReasonerTriple fact = new ReasonerTriple("s1", "a" , "Student");
        ReasonerTriple fact2 = new ReasonerTriple("s2", "a" , "Person");
        p.addFact(fact);
        p.addFact(fact2);
        p.materialize();
        assertEquals(5,p.getFactSize());
        assertEquals(new ReasonerTriple("s1", "a" , "Person"),p.getFacts().get(2));
        assertEquals(new ReasonerTriple("s2", "a" , "Mammal"),p.getFacts().get(3));
        assertEquals(new ReasonerTriple("s1", "a" , "Mammal"),p.getFacts().get(4));

    }

    @Test
    public void joinRuleEvaluation(){

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Teacher"),new ReasonerTriple("?x", "a", "Person"), new ReasonerTriple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s2", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "a" , "Person"));
        p.addFact(new ReasonerTriple("c1", "a" , "Course"));

        p.materialize();
        p.getFacts().forEach(System.out::println);
        assertEquals(5,p.getFactSize());
        assertEquals(new ReasonerTriple("s1", "a" , "Teacher"),p.getFacts().get(4));


    }
    @Test
    public void multipleJoinRuleEvaluation(){

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Teacher"),new ReasonerTriple("?x", "a", "Person"), new ReasonerTriple("?x", "teaches","?c"),new ReasonerTriple("?c", "a","Course"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s2", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("s1", "teaches" , "c1"));
        p.addFact(new ReasonerTriple("c2", "a" , "Course"));

        p.addFact(new ReasonerTriple("c1", "a" , "Course"));
        p.addFact(new ReasonerTriple("s1", "a" , "Person"));

        p.materialize();
        p.getFacts().forEach(System.out::println);

        assertEquals(6,p.getFactSize());
        assertEquals(new ReasonerTriple("s1", "a" , "Teacher"),p.getFacts().get(5));


    }
    @Test
    public void longMultipleJoinRuleEvaluation(){

        Rule r2 = new Rule(new ReasonerTriple("?x", "a", "Q"),new ReasonerTriple("?x", "r", "?y"), new ReasonerTriple("?y", "p","?q"),new ReasonerTriple("?q", "z","?z"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new ReasonerTriple("s", "r" , "o"));
        p.addFact(new ReasonerTriple("o", "p" , "q"));
        p.addFact(new ReasonerTriple("q", "z" , "z"));

        p.addFact(new ReasonerTriple("s2", "r" , "o2"));
        p.addFact(new ReasonerTriple("s", "r" , "o3"));
        p.addFact(new ReasonerTriple("o2", "p" , "q"));
        p.addFact(new ReasonerTriple("o2", "p" , "q2"));
        p.addFact(new ReasonerTriple("q", "z" , "z2"));
        p.addFact(new ReasonerTriple("q2", "z" , "z"));


        p.materialize();
        p.getFacts().forEach(System.out::println);

        assertEquals(9+5,p.getFactSize());
        assertEquals(new ReasonerTriple("s", "a" , "Q"),p.getFacts().get(9));
        assertEquals(new ReasonerTriple("s2", "a" , "Q"),p.getFacts().get(13));

    }

    @Test
    public void commonsGraphTest(){
        ReasonerTriple head = new ReasonerTriple("?x", "a", "Person");
        ReasonerTriple body1 = new ReasonerTriple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        Triple commonsTriple = RDFUtils.createTriple(RDFUtils.createIRI("s"), RDFUtils.createIRI("a"), RDFUtils.createIRI("Student"));
        Graph g = RDFUtils.createGraph();
        g.add(commonsTriple);
        p.addFacts(g);
        p.materialize();
        assertEquals(2,p.getFactSize());
        assertEquals(new ReasonerTriple("s", "a" , "Person"),p.getFacts().get(1));
    }
}
