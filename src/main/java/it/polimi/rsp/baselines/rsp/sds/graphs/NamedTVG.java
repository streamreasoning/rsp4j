package it.polimi.rsp.baselines.rsp.sds.graphs;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;

@Log4j
@Getter
public class NamedTVG extends TimeVaryingGraph {
    private EPStatement statement;

    public NamedTVG(Maintenance maintenance, EPStatement stmt) {
        super(maintenance);
        this.statement = stmt;
    }

    public NamedTVG(Maintenance maintenance, Graph g, EPStatement stmt) {
        super(maintenance, g);
        this.statement = stmt;
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + esp.getEPRuntime().getCurrentTime());

        if (stmt != statement) {
            throw new RuntimeException("Wrong Statement");
        } else {
            super.update(newData, oldData, stmt, esp);
            notifyObservers(esp);
            log.info("NOTIFY");
        }
    }

    @Override
    public EPStatement getTriggeringStatement() {
        return statement;
    }


}