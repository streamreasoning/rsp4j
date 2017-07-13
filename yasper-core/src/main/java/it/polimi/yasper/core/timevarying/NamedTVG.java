package it.polimi.yasper.core.timevarying;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class NamedTVG extends TimeVaryingGraph {

    public NamedTVG(Maintenance maintenance, InstantaneousItem g, WindowOperator statement) {
        super(maintenance, g, statement);
        statement.addListener(this);
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
//        EPStatementImpl epli = (EPStatementImpl) stmt;
//        View o = (OutputProcessViewConditionSnapshot) epli.getParentView();
//        TimeWindowView views = (TimeWindowView) o.getParent();
        long currentTime = esp.getEPRuntime().getCurrentTime();

        if (!window_operator.equals(stmt)) {
            throw new RuntimeException("Wrong Statement");
        } else {
            super.update(newData, oldData, stmt, esp);
            notifyObservers(this);
        }
    }

    @Override
    public WindowOperator getTriggeringStatement() {
        return window_operator;
    }


}