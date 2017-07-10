package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.TimeVaryingItem;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class NamedWindow extends WindowOperator {

    public NamedWindow(Maintenance maintenance, TimeVaryingItem g, EPStatement statement) {
        super(maintenance, g, statement);
        statement.addListener(this);
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