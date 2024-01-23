package org.streamreasoning.rsp4j.csparql2.operators;

import org.streamreasoning.rsp4j.esper.operators.r2s.JDStream;
import org.streamreasoning.rsp4j.esper.operators.r2s.JIStream;
import org.streamreasoning.rsp4j.esper.operators.r2s.JRStream;
import org.apache.jena.sparql.engine.binding.Binding;

public class JenaStreamOperators {

    class JenaDStream extends JDStream<Binding> {

        public JenaDStream(int i) {
            super(i);
        }
    }

    class JenaRStream extends JRStream<Binding> {

    }

    class JenaIStream extends JIStream<Binding> {

        public JenaIStream(int i) {
            super(i);
        }
    }

}
