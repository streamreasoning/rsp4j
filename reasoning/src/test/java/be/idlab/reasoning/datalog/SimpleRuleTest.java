package be.idlab.reasoning.datalog;


import org.junit.Test;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleRuleTest {

    @Test
    public void addFactTest(){
        Triple t = new Triple("s", "p" , "o");
        DatalogProgram p = new DatalogProgram();
        p.addFact(t);
        assertEquals(1,p.getFactSize());
    }

    @Test
    public void addRuleTest(){
        VarOrTerm x = new VarImpl("x");
        VarOrTerm t = new TermImpl("t");
        VarOrTerm y = new VarImpl("y");
        Triple head = new Triple(x,t,y);
        Triple body1 = new Triple(x,t,y);
        Triple body2 = new Triple(x,t,y);
        Rule r = new Rule(head,body1, body2);
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);

        assertEquals(1,p.getRuleSize());
    }
    @Test
    public void simpleRuleEvaluation(){
        Triple head = new Triple("?x", "a", "Person");
        Triple body1 = new Triple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        Triple fact = new Triple("s", "a" , "Student");
        p.addFact(fact);
        p.materialize();
        assertEquals(2,p.getFactSize());
        assertEquals(new Triple("s", "a" , "Person"),p.getFacts().get(1));
    }
    @Test
    public void doubleRuleEvaluation(){
        Triple head = new Triple("?x", "a", "Person");
        Triple body1 = new Triple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        Rule r2 = new Rule(new Triple("?x", "a", "Mammal"),new Triple("?x", "a", "Person"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        Triple fact = new Triple("s", "a" , "Student");
        p.addFact(fact);
        p.materialize();
        assertEquals(3,p.getFactSize());
        assertEquals(new Triple("s", "a" , "Person"),p.getFacts().get(1));
        assertEquals(new Triple("s", "a" , "Mammal"),p.getFacts().get(2));

    }
    @Test
    public void doubleFactEvaluation(){
        Triple head = new Triple("?x", "a", "Person");
        Triple body1 = new Triple("?x", "a", "Student");
        Rule r = new Rule(head,body1);
        Rule r2 = new Rule(new Triple("?x", "a", "Mammal"),new Triple("?x", "a", "Person"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);
        p.addRule(r2);
        Triple fact = new Triple("s1", "a" , "Student");
        Triple fact2 = new Triple("s2", "a" , "Person");
        p.addFact(fact);
        p.addFact(fact2);
        p.materialize();
        assertEquals(5,p.getFactSize());
        assertEquals(new Triple("s1", "a" , "Person"),p.getFacts().get(2));
        assertEquals(new Triple("s2", "a" , "Mammal"),p.getFacts().get(3));
        assertEquals(new Triple("s1", "a" , "Mammal"),p.getFacts().get(4));

    }

    @Test
    public void joinRuleEvaluation(){

        Rule r2 = new Rule(new Triple("?x", "a", "Teacher"),new Triple("?x", "a", "Person"), new Triple("?x", "teaches","?c"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new Triple("s2", "teaches" , "c1"));
        p.addFact(new Triple("s1", "teaches" , "c1"));
        p.addFact(new Triple("s1", "a" , "Person"));
        p.addFact(new Triple("c1", "a" , "Course"));

        p.materialize();
        p.getFacts().forEach(System.out::println);
        assertEquals(5,p.getFactSize());
        assertEquals(new Triple("s1", "a" , "Teacher"),p.getFacts().get(4));


    }
    @Test
    public void multipleJoinRuleEvaluation(){

        Rule r2 = new Rule(new Triple("?x", "a", "Teacher"),new Triple("?x", "a", "Person"), new Triple("?x", "teaches","?c"),new Triple("?c", "a","Course"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new Triple("s2", "teaches" , "c1"));
        p.addFact(new Triple("s1", "teaches" , "c1"));
        p.addFact(new Triple("c2", "a" , "Course"));

        p.addFact(new Triple("c1", "a" , "Course"));
        p.addFact(new Triple("s1", "a" , "Person"));

        p.materialize();
        p.getFacts().forEach(System.out::println);

        assertEquals(6,p.getFactSize());
        assertEquals(new Triple("s1", "a" , "Teacher"),p.getFacts().get(5));


    }
    @Test
    public void longMultipleJoinRuleEvaluation(){

        Rule r2 = new Rule(new Triple("?x", "a", "Q"),new Triple("?x", "r", "?y"), new Triple("?y", "p","?q"),new Triple("?q", "z","?z"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r2);
        p.addFact(new Triple("s", "r" , "o"));
        p.addFact(new Triple("o", "p" , "q"));
        p.addFact(new Triple("q", "z" , "z"));

        p.addFact(new Triple("s2", "r" , "o2"));
        p.addFact(new Triple("s", "r" , "o3"));
        p.addFact(new Triple("o2", "p" , "q"));
        p.addFact(new Triple("o2", "p" , "q2"));
        p.addFact(new Triple("q", "z" , "z2"));
        p.addFact(new Triple("q2", "z" , "z"));


        p.materialize();
        p.getFacts().forEach(System.out::println);

        assertEquals(9+5,p.getFactSize());
        assertEquals(new Triple("s", "a" , "Q"),p.getFacts().get(9));
        assertEquals(new Triple("s2", "a" , "Q"),p.getFacts().get(13));

    }
}
