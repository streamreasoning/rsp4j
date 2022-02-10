package org.streamreasoning.rsp4j.operatorapi.monitoring;

import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

/***
 * This class is used monitor RSP4J components.
 * Monitored components can report their metrics to the RSP4JMonitor.
 * Interested parties to the monitor results can consume the stream.
 */
public class RSP4JMonitor {

    private static DataStream<Metric> resultStream ;

    private RSP4JMonitor(){}
    public static void reportEvalTime(String name, String eval, long resultTime) {

        checkIfStreamExistsAndCreate();
        resultStream.put(new Metric(name,eval,"EvalTime",resultTime,System.currentTimeMillis()),System.currentTimeMillis());
    }

    private static void checkIfStreamExistsAndCreate() {
        if(resultStream == null){
            resultStream = new DataStreamImpl<>("http://rsp4j.io/monitoring/");
        }
    }

    public static DataStream<Metric> getMonitoringStream(){
        checkIfStreamExistsAndCreate();
        return resultStream;
    }
}
