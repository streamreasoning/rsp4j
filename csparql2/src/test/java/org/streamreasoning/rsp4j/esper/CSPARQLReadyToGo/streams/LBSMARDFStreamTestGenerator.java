/*******************************************************************************
 * Copyright 2014 Davide Barbieri, Emanuele Della Valle, Marco Balduini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Acknowledgements:
 *
 * This work was partially supported by the European project LarKC (FP7-215535) 
 * and by the European project MODAClouds (FP7-318484)
 ******************************************************************************/
package org.streamreasoning.rsp4j.esper.CSPARQLReadyToGo.streams;


import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

public class LBSMARDFStreamTestGenerator extends DataStreamImpl implements Runnable {

    /**
     * The logger.
     */
    protected final Logger logger = LoggerFactory.getLogger(LBSMARDFStreamTestGenerator.class);

    private int c = 1;
    private int ct = 1;
    private boolean keepRunning = false;

    protected int grow_rate;
    private DataStream<Graph> s;

    private String type;

    public LBSMARDFStreamTestGenerator(String name, String stream_uri, int grow_rate) {
        super(stream_uri);
        this.type = name;
        this.grow_rate = grow_rate;
    }

    public void setWritable(DataStream<Graph> e) {
        this.s = e;
    }

    public void pleaseStop() {
        keepRunning = false;
    }

    @Override
    public void run() {

        int i = 1;

        try {

            Model m;
            String uri = "http://www.streamreasoning/jasper/test#";
            Resource subject;
            Property property;
            Resource object;
            int appTimestamp1 = 0;

            while (true) {
                appTimestamp1 = i * 2000;

                m = ModelFactory.createDefaultModel();

                subject = ResourceFactory.createResource(uri + "user" + this.c);
                property = ResourceFactory.createProperty(uri + "likes");
                object = ResourceFactory.createResource(uri + "O" + this.c);

                m.add(m.createStatement(subject, property, object));

                System.out.println("At [" + appTimestamp1 + "] [" + System.currentTimeMillis() + "] Sending [" + m.getGraph() + "] on " + stream_uri);

                double n = Math.random() * 5;

                for (int j = 0; j < n; j++) {
                    subject = ResourceFactory.createResource(uri + "user" + this.c + j);
                    property = ResourceFactory.createProperty(uri + "likes");
                    object = ResourceFactory.createResource(uri + "O" + this.c);
                    Literal ts = ResourceFactory.createTypedLiteral(new Long(appTimestamp1));
                    Property tsp = ResourceFactory.createProperty(uri + "timestamp");
                    m.add(m.createStatement(subject, tsp, ts));
                    m.add(m.createStatement(subject, property, object));
                }

                if (s != null)
                    this.s.put(m.getGraph(), appTimestamp1);

                i++;
                Thread.sleep(500);
                this.c++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
