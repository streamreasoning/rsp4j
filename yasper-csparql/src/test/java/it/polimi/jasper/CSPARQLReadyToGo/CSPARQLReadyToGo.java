package it.polimi.jasper.CSPARQLReadyToGo;

import it.polimi.sr.rsp.csparql.engine.CSPARQLEngine;
import it.polimi.jasper.CSPARQLReadyToGo.streams.LBSMARDFStreamTestGenerator;
import it.polimi.sr.rsp.csparql.formatter.sysout.GenericResponseSysOutFormatter;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDSConfiguration;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.jena.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Riccardo on 03/08/16.
 */
public class CSPARQLReadyToGo {

    static CSPARQLEngine sr;

    public static void main(String[] args) throws InterruptedException, IOException, ConfigurationException {

        // examples name
        final int WHO_LIKES_WHAT = 0;
        final int HOW_MANY_USERS_LIKE_THE_SAME_OBJ = 1;
        final int MULTI_STREAM = 2;

        // put here the example you want to run
        int key = WHO_LIKES_WHAT;

        String path = CSPARQLReadyToGo.class.getResource("/csparql.properties").getPath();
        SDSConfiguration config = new SDSConfiguration(path);
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");

        ContinuousQuery q;
        ContinuousQueryExecution cqe;
        LBSMARDFStreamTestGenerator writer;
        DataStreamImpl<Graph> register;

        sr = new CSPARQLEngine(0, ec);

        switch (key) {
            case WHO_LIKES_WHAT:
                System.out.println("WHO_LIKES_WHAT example");

                writer = new LBSMARDFStreamTestGenerator("Writer", "http://streamreasoning.org/jasper/streams/stream2", 5);
                register = sr.register(writer);
                writer.setWritable(register);

                cqe = sr.register(getQuery("rtgp-q1", ".rspql"), config);
                q = cqe.getContinuousQuery();
                cqe.add(new GenericResponseSysOutFormatter("TABLE", true));

                cqe.getSDS();
                cqe.getR2R();
                cqe.getS2R();
                cqe.getR2R();
//
//                cqe.<Table>outstream().addConsumer((arg, ts) -> {
//                    s
//                });

                System.out.println(q.toString());
                System.out.println("<<------>>");

                //In real application we do not have to start the stream.
                (new Thread(writer)).start();

                break;
            case HOW_MANY_USERS_LIKE_THE_SAME_OBJ:
                writer = new LBSMARDFStreamTestGenerator("Writer", "http://streamreasoning.org/jasper/streams/stream2", 5);
                register = sr.register(writer);
                writer.setWritable(register);

                cqe = sr.register(getQuery("rtgp-q2", ".rspql"), config);
                q = cqe.getContinuousQuery();
                cqe.add(new GenericResponseSysOutFormatter("TABLE", true));

                System.out.println(q.toString());
                System.out.println("<<------>>");

                //In real application we do not have to start the stream.
                (new Thread(writer)).start();
                break;

            case MULTI_STREAM:
                writer = new LBSMARDFStreamTestGenerator("Writer", "http://streamreasoning.org/jasper/streams/stream2", 5);
                register = sr.register(writer);
                writer.setWritable(register);

                LBSMARDFStreamTestGenerator writer2 = new LBSMARDFStreamTestGenerator("Writer", "http://streamreasoning.org/jasper/streams/stream3", 5);
                DataStreamImpl<Graph> register2 = sr.register(writer2);
                writer2.setWritable(register2);

                cqe = sr.register(getQuery("rtgp-q3", ".rspql"), config);
                q = cqe.getContinuousQuery();
                cqe.add(new GenericResponseSysOutFormatter("TABLE", true));

                cqe.outstream().addConsumer((arg, ts) -> {


                });

                System.out.println(q.toString());
                System.out.println("<<------>>");

                //In real application we do not have to start the stream.
                (new Thread(writer)).start();
                (new Thread(writer2)).start();
                break;


            default:
                System.exit(0);
                break;
        }
    }

    public static String getQuery(String queryName, String suffix) throws IOException {
        URL resource = CSPARQLReadyToGo.class.getResource("/" + queryName + suffix);
        System.out.println(resource.getPath());
        File file = new File(resource.getPath());
        return FileUtils.readFileToString(file);
    }

}
