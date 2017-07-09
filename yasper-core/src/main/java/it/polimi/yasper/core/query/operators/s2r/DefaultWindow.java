package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.TimeVaryingGraph;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.HashSet;
import java.util.Set;

@Log4j
@Getter
public class DefaultWindow extends WindowOperator {

    private Set<EPStatement> statements;

    public DefaultWindow(Maintenance maintenance, TimeVaryingGraph g) {
        super(maintenance, g, null);
        this.statements = new HashSet<>();
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + esp.getEPRuntime().getCurrentTime());

        super.update(newData, oldData, stmt, esp);

        this.statement = stmt;
        notifyObservers(esp);

    }

    public void addStatement(EPStatement stmt) {
        statements.add(stmt);
        stmt.addListener(this);
    }

    @Override
    public EPStatement getTriggeringStatement() {
        return statement;
    }


}