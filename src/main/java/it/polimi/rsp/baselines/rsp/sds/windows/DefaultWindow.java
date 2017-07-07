package it.polimi.rsp.baselines.rsp.sds.windows;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.rsp.query.reasoning.TimeVaryingInfGraph;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.HashSet;
import java.util.Set;

@Log4j
@Getter
public class DefaultWindow extends WindowOperator {

    private Set<EPStatement> statements;
    private EPStatement latest;


    public DefaultWindow(Maintenance maintenance, Set<EPStatement> stmts) {
        super(maintenance);
        this.statements = stmts;
    }

    public DefaultWindow(Maintenance maintenance, TimeVaryingGraph g, Set<EPStatement> stmts) {
        super(maintenance, g);
        this.statements = stmts;
    }

    public DefaultWindow(Maintenance maintenanceType, TimeVaryingInfGraph bind) {
        this(maintenanceType, bind, new HashSet<EPStatement>());
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