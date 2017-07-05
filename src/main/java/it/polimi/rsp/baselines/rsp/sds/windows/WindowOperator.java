package it.polimi.rsp.baselines.rsp.sds.windows;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.rsp.stream.element.StreamItem;
import lombok.extern.log4j.Log4j;

/**
 * Created by riccardo on 05/07/2017.
 */
@Log4j
public abstract class WindowOperator extends WindowModel {

    private Maintenance maintenanceType;


    public WindowOperator(Maintenance maintenance) {
        super();
        this.maintenanceType = maintenance;
    }

    public WindowOperator(Maintenance maintenance, TimeVaryingGraph g) {
        super(g);
        this.maintenanceType = maintenance;
        g.setWindowOperator(this);
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        long currentTime = esp.getEPRuntime().getCurrentTime();
        long l = System.currentTimeMillis();

        log.info("[" + Thread.currentThread() + "][" + l + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + currentTime);

        graph.setTimestamp(currentTime);

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


