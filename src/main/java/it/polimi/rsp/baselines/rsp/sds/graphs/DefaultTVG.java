package it.polimi.rsp.baselines.rsp.sds.graphs;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;

import java.util.Set;

@Log4j
@Getter
public class DefaultTVG extends TimeVaryingGraph {

    private Set<EPStatement> statements;
    private EPStatement latest;

    public DefaultTVG(Maintenance maintenance, Set<EPStatement> stmts) {
        super(maintenance);
        this.statements = stmts;
    }

    public DefaultTVG(Maintenance maintenance, Graph g, Set<EPStatement> stmts) {
        super(maintenance, g);
        this.statements = stmts;
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + esp.getEPRuntime().getCurrentTime());

        super.update(newData, oldData, stmt, esp);

        this.latest = stmt;
        notifyObservers(esp);

    }

    @Override
    public EPStatement getTriggeringStatement() {
        return latest;
    }


}