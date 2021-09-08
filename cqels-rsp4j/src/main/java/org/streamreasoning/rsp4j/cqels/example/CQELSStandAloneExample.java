package org.streamreasoning.rsp4j.cqels.example;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.tdb.store.NodeId;
import org.deri.cqels.data.Mapping;
import org.deri.cqels.engine.ContinuousListener;
import org.deri.cqels.engine.ContinuousSelect;
import org.deri.cqels.engine.ExecContext;
import org.deri.cqels.integration.CQELSInjector;

public class CQELSStandAloneExample {

    public static void main(String[] args) throws InterruptedException {

        String query1 = "SELECT ?x ?z ?p WHERE {"
                + "STREAM <ws://localhost:8124/tw/stream> [RANGE 15s] {?x <http://test/near> ?z. ?x <http://test/near> <http://test/locStart>}"
                + "STREAM <ws://localhost:8124/tw/stream2> [RANGE 15s] {?x <http://test/knows> ?p. ?x <http://test/knows> <http://test/startPerson>}"
                + "}";
        //====================INITIALIZE CQELS=================================================//
        final String CQELSHOME = "/tmp/cqels/";
        ExecContext execContext = new ExecContext(CQELSHOME, true);


        ContinuousSelect continuousSelect = execContext.registerSelect(query1);
        ContinuousListener myListener = new MappingListerner(execContext);
        continuousSelect.register(myListener);
        execContext.engine().send(Node.createURI("ws://localhost:8124/tw/stream"), Triple.create(
                Node.createURI("http://test/person"),
                Node.createURI("http://test/near"),
                Node.createURI("http://test/locStart")));
        execContext.engine().send(Node.createURI("ws://localhost:8124/tw/stream2"),
                Triple.create(
                Node.createURI("http://test/person"),
                Node.createURI("http://test/knows"),
                Node.createURI("http://test/startPerson")));


        for(int i = 0 ; i < 100; i++) {
            execContext.engine().send(Node.createURI("ws://localhost:8124/tw/stream"),
                    Triple.create(
                    Node.createURI("http://test/person"),
                    Node.createURI("http://test/near"),
                    Node.createURI("http://test/loc"+i)));
            execContext.engine().send(Node.createURI("ws://localhost:8124/tw/stream2"),
                    Triple.create(
                    Node.createURI("http://test/person"),
                    Node.createURI("http://test/knows"),
                    Node.createURI("http://test/person"+i)));
            Thread.sleep(1000);

        }




    }

    static class MappingListerner implements ContinuousListener{

        private final ExecContext execContext;

        public MappingListerner(ExecContext execContext){
            this.execContext = execContext;
        }

        @Override
        public void update(Mapping mapping) {
            System.out.println(mapping);

            //System.out.println(execContext.dictionary().getNodeForNodeId(new NodeId(mapping.get(Var.alloc("q")))));
            //System.out.println(execContext.dictionary().getNodeForNodeId(new NodeId(mapping.get(Var.alloc("z")))));
            //System.out.println(execContext.dictionary().getNodeForNodeId(new NodeId(mapping.get("?z"))));

        }
    }
}
