package it.polimi.rsp.baselines.jena.sds.observer;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.rsp.baselines.enums.OntoLanguage;
import it.polimi.rsp.baselines.enums.Reasoning;
import it.polimi.rsp.baselines.esper.RSPListener;
import it.polimi.rsp.baselines.jena.events.stimuli.BaselineStimulus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.Observable;

@Log4j
@Data
public abstract class TimeVaryingGraph extends Observable implements RSPListener {

    private Reasoning reasoningType;
    private Graph graph;


    public TimeVaryingGraph(Reasoning reasoning) {
        this.reasoningType = reasoning;
        this.graph = ModelFactory.createDefaultModel().getGraph();
    }

    public TimeVaryingGraph(Reasoning reasoning, Graph g) {
        this.reasoningType = reasoning;
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


    private void handleSingleIStream(BaselineStimulus underlying) {
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
                    if (meb.getProperties() instanceof BaselineStimulus) {
                        handleSingleIStream((BaselineStimulus) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            BaselineStimulus underlying = (BaselineStimulus) meb.get("stream_" + i);
                            handleSingleIStream(underlying);
                        }
                    }
                }
            }
        }
    }

    private void handleSingleDStream(BaselineStimulus underlying) {
        log.debug("Handling single Istream [" + underlying + "]");
        graph = underlying.removeFrom(graph);
    }

    protected void DStreamUpdate(EventBean[] oldData) {
        if (oldData != null && Reasoning.INCREMENTAL.equals(reasoningType)) { // TODO
            log.debug("[" + oldData.length + "] Old Events of type ["
                    + oldData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : oldData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof BaselineStimulus) {
                        handleSingleDStream((BaselineStimulus) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            BaselineStimulus underlying = (BaselineStimulus) meb.get("stream_" + i);
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