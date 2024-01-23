package org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins;

import org.junit.Test;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class JoinAlgorithmTests {

    @Test
    public void testNestedNoJoin(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createUnique();

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);
        Binding expected = left.iterator().next().union(right.iterator().next());
        assertEquals(Collections.singleton(expected), joined);

    }
    @Test
    public void testHashNoJoin(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createUnique();

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        Binding expected = left.iterator().next().union(right.iterator().next());
        assertEquals(Collections.singleton(expected), joined);
    }
    @Test
    public void testNestedJoin(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(createExpected(), joined);

    }
    @Test
    public void testHashJoin(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(createExpected(), joined);
    }
    @Test
    public void testNestedJoinEmptyLeft(){
        Set<Binding> left = new HashSet<>();
        Set<Binding> right = createInitialRight();

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(Collections.emptySet(), joined);
    }
    @Test
    public void testNestedJoinEmptyRight(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = new HashSet<>();

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(Collections.emptySet(), joined);
    }
    @Test
    public void testHashJoinEmptyLeft(){
        Set<Binding> left = new HashSet<>();
        Set<Binding> right = createInitialRight();

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(Collections.emptySet(), joined);
    }
    @Test
    public void testHashJoinEmptyRight(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = new HashSet<>();

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(Collections.emptySet(), joined);
    }

    @Test
    public void testNestedJoinMultipleLeft(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();
        left = extendLeft(left,"red");

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(createExpected(), joined);

    }
    @Test
    public void testHashJoinMultipleLeft(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();
        left = extendLeft(left,"red");

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(createExpected(), joined);

    }
    @Test
    public void testNestedJoinMultipleRight(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();
        right = extendRight(right,"red");

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(createExpected(), joined);

    }
    @Test
    public void testHashJoinMultipleRight(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();
        right = extendRight(right,"red");

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(createExpected(), joined);

    }
    @Test
    public void testNestedJoinMultipleLeftAndRight(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();
        left = extendLeft(left,"red");
        right = extendRight(right,"red");

        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(extendExpected(createExpected(),"red"), joined);

    }
    @Test
    public void testHashJoinMultipleLeftAndRight(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();
        left = extendLeft(left,"red");
        right = extendRight(right,"red");

        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(extendExpected(createExpected(),"red"), joined);

    }

    @Test
    public void testNestedJoinMultipleVars(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();

        for(Binding b:right){
            b.add(new VarImpl("o"),new TermImpl("NoColor"));
        }
        JoinAlgorithm<Binding> joinAlgorithm = new NestedJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(Collections.emptySet(), joined);
    }
    @Test
    public void testHashJoinMultipleVars(){
        Set<Binding> left = createInitialLeft();
        Set<Binding> right = createInitialRight();

        for(Binding b:right){
            b.add(new VarImpl("o"),new TermImpl("NoColor"));
        }
        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        Set<Binding> joined = joinAlgorithm.join(left,right);

        assertEquals(Collections.emptySet(), joined);
    }

    private Set<Binding> createInitialLeft(){
        Set<Binding> left = new HashSet<>();
        return extendLeft(left,"blue");
    }
    private Set<Binding> extendLeft(Set<Binding> left, String colorName){
        Binding b1 = new BindingImpl();
        Var varS = new VarImpl("s");
        Var varO = new VarImpl("o");
        b1.add(varS, new TermImpl(colorName));
        b1.add(varO, new TermImpl("Color"));
        left.add(b1);
        return left;
    }
    private Set<Binding> createInitialRight(){
        Set<Binding> right = new HashSet<>();
        return extendRight(right,"blue");
    }
    private Set<Binding> extendRight(Set<Binding> right, String colorName){
        Var varS = new VarImpl("s");
        Var varP = new VarImpl("p");
        Binding b2 = new BindingImpl();
        b2.add(varS, new TermImpl(colorName));
        b2.add(varP, new TermImpl("test"));
        right.add(b2);
        return right;
    }
    private Set<Binding> createExpected(){
        Set<Binding> joined = new HashSet<>();
        return extendExpected(joined,"blue");
    }
    private Set<Binding> extendExpected(Set<Binding> joined, String colorName){
        Binding b1 = new BindingImpl();
        Var varS = new VarImpl("s");
        Var varO = new VarImpl("o");
        Var varP = new VarImpl("p");

        b1.add(varS, new TermImpl(colorName));
        b1.add(varO, new TermImpl("Color"));
        b1.add(varP, new TermImpl("test"));
        joined.add(b1);
        return joined;
    }
    private Set<Binding> createUnique(){
        Set<Binding> right = new HashSet<>();
        Var varS = new VarImpl("sUnique");
        Var varP = new VarImpl("pUnique");
        Binding b2 = new BindingImpl();
        b2.add(varS, new TermImpl("colorName"));
        b2.add(varP, new TermImpl("test"));
        right.add(b2);
        return right;
    }
}
