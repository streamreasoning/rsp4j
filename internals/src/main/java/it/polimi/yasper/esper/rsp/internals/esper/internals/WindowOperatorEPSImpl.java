package it.polimi.yasper.esper.rsp.internals.esper.internals;

import com.espertech.esper.collection.TimeWindow;
import com.espertech.esper.core.service.EPStatementImpl;
import com.espertech.esper.core.service.StatementContext;
import com.espertech.esper.core.service.StatementLifecycleSvc;
import com.espertech.esper.core.service.StatementMetadata;
import com.espertech.esper.dispatch.DispatchService;
import com.espertech.esper.timer.TimeSourceService;
import it.polimi.rspql.Stream;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.rspql.Window;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.yasper.core.enums.WindowType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by riccardo on 04/09/2017.
 */
public class WindowOperatorEPSImpl extends EPStatementImpl implements WindowOperator {

    @Setter
    @Getter
    private TimeWindow window;

    /**
     * Ctor.
     *
     * @param expressionNoAnnotations expression text witout annotations
     * @param isPattern               is true to indicate this is a pure pattern expression
     * @param dispatchService         for dispatching events to listeners to the statement
     * @param statementLifecycleSvc   handles lifecycle transitions for the statement
     * @param timeLastStateChange     the timestamp the statement was created and started
     * @param isBlockingDispatch      is true if the dispatch to listeners should block to preserve event generation order
     * @param isSpinBlockingDispatch  true to use spin locks blocking to deliver results, as locks are usually uncontended
     * @param msecBlockingTimeout     is the max number of milliseconds of block time
     * @param timeSourceService       time source provider
     * @param statementMetadata       statement metadata
     * @param userObject              the application define user object associated to each statement, if supplied
     * @param statementContext        the statement service context
     * @param isFailed                indicator to start in failed state
     * @param nameProvided            true to indicate a statement name has been provided and is not a system-generated name
     */
    public WindowOperatorEPSImpl(String expressionNoAnnotations, boolean isPattern, DispatchService dispatchService, StatementLifecycleSvc statementLifecycleSvc, long timeLastStateChange, boolean isBlockingDispatch, boolean isSpinBlockingDispatch, long msecBlockingTimeout, TimeSourceService timeSourceService, StatementMetadata statementMetadata, Object userObject, StatementContext statementContext, boolean isFailed, boolean nameProvided) {
        super(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, isBlockingDispatch, isSpinBlockingDispatch, msecBlockingTimeout, timeSourceService, statementMetadata, userObject, statementContext, isFailed, nameProvided);
    }

    @Override
    public int getT0() {
        return 0;
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public int getStep() {
        return 0;
    }

    @Override
    public String getUnitRange() {
        return null;
    }

    @Override
    public String getUnitStep() {
        return null;
    }

    @Override
    public TimeVarying apply(Stream s) {
        return null;
    }

    @Override
    public Window getWindowContent(long t0) {
        return null;
    }

    @Override
    public boolean isNamed() {
        return false;
    }

    @Override
    public WindowType getType() {
        return null;
    }
}
