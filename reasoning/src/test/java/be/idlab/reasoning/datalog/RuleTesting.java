package be.idlab.reasoning.datalog;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RuleTesting {
    @Test
    public void testFreeVariablesRenaming(){
        Rule r = new Rule(new Triple("?p", "a", "Person"),new Triple("?p", "a", "?freeVariable"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);

        Rule newRule = p.renameFreeVariables(r);
        Triple freeVarTriple = newRule.getBody().get(0);
        System.out.println(freeVarTriple);
        assertNotEquals(freeVarTriple.getObject().toString(),"freeVariable");
    }
    @Test
    public void testRuleSubsitution(){
        Triple toRewriteTo = new Triple("?x", "a", "Person");
        Rule r = new Rule(new Triple("?p", "a", "Person"),new Triple("?p", "a", "Student"));
        DatalogProgram p = new DatalogProgram();
        p.addRule(r);

        Rule alignedrule = p.alignHeadVariableNames(toRewriteTo,r);
        assertEquals("x",alignedrule.getHead().getSubject().toString());
        assertEquals("x",alignedrule.getBody().get(0).getSubject().toString());

    }
}
