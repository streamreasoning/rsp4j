package it.polimi.rsp.baselines.rsp.sds.windows;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class NamedWindow extends WindowOperator {
    private EPStatement statement;

    public NamedWindow(Maintenance maintenance, EPStatement stmt) {
        super(maintenance);
        this.statement = stmt;
    }

    public NamedWindow(Maintenance maintenance, TimeVaryingGraph g, EPStatement stmt) {
        super(maintenance, g);
        this.statement = stmt;
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        long currentTime = esp.getEPRuntime().getCurrentTime();
        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + currentTime);

        if (stmt != statement) {
            throw new RuntimeException("Wrong Statement");
        } else {
            super.update(newData, oldData, stmt, esp);
            notifyObservers(currentTime);
            log.info("NOTIFY");
        }
    }

    @Override
    public EPStatement getTriggeringStatement() {
        return statement;
    }


}