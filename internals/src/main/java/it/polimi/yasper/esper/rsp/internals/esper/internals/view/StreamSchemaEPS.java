package it.polimi.yasper.esper.rsp.internals.esper.internals.view;

import com.espertech.esper.core.service.EPStatementImpl;
import com.espertech.esper.core.service.StatementContext;
import com.espertech.esper.core.service.StatementLifecycleSvc;
import com.espertech.esper.core.service.StatementMetadata;
import com.espertech.esper.dispatch.DispatchService;
import com.espertech.esper.timer.TimeSourceService;

/**
 * Created by riccardo on 04/09/2017.
 */
public class StreamSchemaEPS extends EPStatementImpl
{
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
    public StreamSchemaEPS(String expressionNoAnnotations, boolean isPattern, DispatchService dispatchService, StatementLifecycleSvc statementLifecycleSvc, long timeLastStateChange, boolean isBlockingDispatch, boolean isSpinBlockingDispatch, long msecBlockingTimeout, TimeSourceService timeSourceService, StatementMetadata statementMetadata, Object userObject, StatementContext statementContext, boolean isFailed, boolean nameProvided) {
        super(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, isBlockingDispatch, isSpinBlockingDispatch, msecBlockingTimeout, timeSourceService, statementMetadata, userObject, statementContext, isFailed, nameProvided);
    }
}
