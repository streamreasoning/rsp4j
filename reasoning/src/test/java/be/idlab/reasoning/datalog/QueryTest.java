package be.idlab.reasoning.datalog;

import org.junit.Test;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class QueryTest {

    @Test
    public void simpleTriplePatternTest(){
        DatalogProgram p = new DatalogProgram();
        p.addFact(new Triple("s", "r" , "o"));

        Optional<Set<Binding>> bindings = p.queryAllFacts(new Triple("?s","?p","?o"));
        assertEquals(1,bindings.get().size());
    }

    @Test
    public void simpleTriplePatternTestMultipleMatches(){
        DatalogProgram p = new DatalogProgram();
        p.addFact(new Triple("s", "r" , "o"));
        p.addFact(new Triple("s2", "r2" , "o2"));
        p.addFact(new Triple("s2", "r2" , "o3"));

        Optional<Set<Binding>> bindings = p.queryAllFacts(new Triple("?s","?p","?o"));
        assertEquals(3,bindings.get().size());
    }
    @Test
    public void simpleJoinTriplePatternTest(){
        DatalogProgram p = new DatalogProgram();
        p.addFact(new Triple("s", "p" , "o"));
        p.addFact(new Triple("o", "a" , "C"));
        p.addFact(new Triple("o", "a" , "B"));
        p.addFact(new Triple("q", "a" , "B"));

        Optional<Set<Binding>> bindings = p.queryAllFacts(new Triple("?s","p","?o"),new Triple("?o","a","C"));
        assertEquals(1,bindings.get().size());
        System.out.println(bindings);
    }
    @Test
    public void longJoinTriplePatternTest(){
        DatalogProgram p = new DatalogProgram();
        p.addFact(new Triple("s", "r" , "o"));
        p.addFact(new Triple("o", "p" , "q"));
        p.addFact(new Triple("q", "z" , "z"));

        p.addFact(new Triple("s2", "r" , "o2"));
        p.addFact(new Triple("s", "r" , "o3"));
        p.addFact(new Triple("o2", "p" , "q"));
        p.addFact(new Triple("o2", "p" , "q2"));
        p.addFact(new Triple("q", "z" , "z2"));
        p.addFact(new Triple("q2", "z" , "z"));

        Optional<Set<Binding>> bindings = p.queryAllFacts(new Triple("?x", "r", "?y"), new Triple("?y", "p","?q"),new Triple("?q", "z","?z"));
        assertEquals(5,bindings.get().size());
        System.out.println(bindings);
    }
    @Test
    public void parlyVarsNoMatchPatternTest(){
        DatalogProgram p = new DatalogProgram();
        p.addFact(new Triple("s", "r" , "o"));
        p.addFact(new Triple("o", "p" , "q2"));


        Optional<Set<Binding>> bindings = p.queryAllFacts(new Triple("?x", "r", "?y"), new Triple("?y", "p","q"),new Triple("q", "z","z"));
        System.out.println(bindings);

        assertFalse(bindings.isPresent());
    }
}
