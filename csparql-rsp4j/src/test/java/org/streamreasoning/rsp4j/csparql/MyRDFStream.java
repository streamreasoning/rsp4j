package org.streamreasoning.rsp4j.csparql;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;

public class MyRDFStream extends RdfStream implements Runnable {
    private int c = 1;
    public MyRDFStream(String iri) {
        super(iri);
    }

    @Override
    public void run() {
        boolean keepRunning = true;

        while (keepRunning) {
            final RdfQuadruple q = new RdfQuadruple(super.getIRI()+"/user" + this.c,
                    "http://myexample.org/likes", "http://myexample.org/O" + this.c, System.currentTimeMillis());

            this.put(q);
            //          logger.info(q.toString());

            double n = Math.random()*5;

            for (int i=0;i<n;i++) {
                final RdfQuadruple q1 = new RdfQuadruple(super.getIRI()+"/user" + this.c+i,
                        "http://myexample.org/likes", "http://myexample.org/O" + this.c, System.currentTimeMillis());
                this.put(q1);
                //         logger.info(q1.toString());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.c++;
        }
    }
}
