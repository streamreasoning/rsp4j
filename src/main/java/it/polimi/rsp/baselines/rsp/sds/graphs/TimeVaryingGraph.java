package it.polimi.rsp.baselines.rsp.sds.graphs;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.stream.RSPListener;
import it.polimi.rsp.baselines.rsp.stream.element.StreamItem;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.Observable;

@Log4j
@Data
public abstract class TimeVaryingGraph extends Observable implements RSPListener {

    private Maintenance maintenanceType;
    private Graph graph;


    public TimeVaryingGraph(Maintenance maintenance) {
        this.maintenanceType = maintenance;
        this.graph = ModelFactory.createDefaultModel().getGraph();
    }

    public TimeVaryingGraph(Maintenance maintenance, Graph g) {
        this.maintenanceType = maintenance;
        this.graph = g;
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + esp.getEPRuntime().getCurrentTime());

        DStreamUpdate(oldData);
        IStreamUpdate(newData);
        setChanged();

    }


    protected void handleSingleIStream(StreamItem underlying) {
        log.debug("Handling single Istream [" + underlying + "]");
        graph = underlying.addTo(graph);
    }

    protected void IStreamUpdate(EventBean[] newData) {
        if (newData != null && newData.length != 0) {
            log.info("[" + newData.length + "] New Events of type ["
                    + newData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : newData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleIStream((StreamItem) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem underlying = (StreamItem) meb.get("stream_" + i);
                            handleSingleIStream(underlying);
                        }
                    }
                }
            }
        }
    }

    protected void handleSingleDStream(StreamItem underlying) {
        log.debug("Handling single Istream [" + underlying + "]");
        graph = underlying.removeFrom(graph);
    }

    protected void DStreamUpdate(EventBean[] oldData) {
        if (oldData != null && Maintenance.INCREMENTAL.equals(maintenanceType)) { // TODO
            log.debug("[" + oldData.length + "] Old Events of type ["
                    + oldData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : oldData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleDStream((StreamItem) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem underlying = (StreamItem) meb.get("stream_" + i);
                            handleSingleDStream(underlying);
                        }
                    }
                }
            }
        } else {
            //Remove all the data
            graph.clear();
        }
    }

    public abstract EPStatement getTriggeringStatement();
}