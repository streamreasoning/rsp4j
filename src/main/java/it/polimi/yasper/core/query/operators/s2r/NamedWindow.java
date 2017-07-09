package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.jena.JenaTimeVaryingGraph;
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

    public NamedWindow(Maintenance maintenance, JenaTimeVaryingGraph g, EPStatement stmt) {
        super(maintenance, g);
        this.statement = stmt;
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
//        EPStatementImpl epli = (EPStatementImpl) stmt;
//        View o = (OutputProcessViewConditionSnapshot) epli.getParentView();
//        TimeWindowView views = (TimeWindowView) o.getParent();
        long currentTime = esp.getEPRuntime().getCurrentTime();

        if (stmt != statement) {
            throw new RuntimeException("Wrong Statement");
        } else {
            super.update(newData, oldData, stmt, esp);
            notifyObservers(this);
        }
    }

    @Override
    public EPStatement getTriggeringStatement() {
        return statement;
    }


}