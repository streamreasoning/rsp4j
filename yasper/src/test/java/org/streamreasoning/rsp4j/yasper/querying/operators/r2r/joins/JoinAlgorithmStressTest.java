package org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins;

import org.junit.Test;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class JoinAlgorithmStressTest {

    @Test
    public void stressHashJoin(){
        Set<Binding> left = createBinding(100000,0);
        Set<Binding> right = createBinding(100000,0);


        JoinAlgorithm<Binding> joinAlgorithm = new HashJoinAlgorithm();
        long t0 = System.currentTimeMillis();
        Set<Binding> joined = joinAlgorithm.join(left,right);
        System.out.println("time: " + (System.currentTimeMillis()-t0));
        assertEquals(100000, joined.size());
    }

    private Set<Binding> createBinding(int size, int seed){
        Set<Binding> bindings = new HashSet<>();
        for(int i = 0 ; i<size; i++){
            Binding b = new BindingImpl();
            b.add(new VarImpl("same"),new TermImpl("value"));
            b.add(new VarImpl("diff_"+seed),new TermImpl("diff_value_" + i + seed));
            bindings.add(b);
        }
        return bindings;
    }
}
