package it.polimi.yasper.esper.rsp.internals.esper.internals;

import com.espertech.esper.core.context.factory.StatementAgentInstanceFactoryResult;
import com.espertech.esper.core.service.*;
import com.espertech.esper.dispatch.DispatchService;
import com.espertech.esper.timer.TimeSourceService;
import com.espertech.esper.util.StopCallback;

/**
 * Created by riccardo on 04/09/2017.
 */
public class EPStatementFactoryRSP implements EPStatementFactory {
    public EPStatementSPI make(String expressionNoAnnotations, boolean isPattern, DispatchService dispatchService, StatementLifecycleSvcImpl statementLifecycleSvc, long timeLastStateChange, boolean preserveDispatchOrder, boolean isSpinLocks, long blockingTimeout, TimeSourceService timeSource, StatementMetadata statementMetadata, Object statementUserObject, StatementContext statementContext, boolean isFailed, boolean nameProvided) {
        EPStatementSPI statement;
        switch (statementMetadata.getStatementType()) {
            case CREATE_SCHEMA:
                statement = getSchemaEPS(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, preserveDispatchOrder, isSpinLocks, blockingTimeout, timeSource, statementMetadata, statementUserObject, statementContext, isFailed, nameProvided);
                break;
            case SELECT:
                //TODO check if it an rsp window
                statement = getWindowOperator(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, preserveDispatchOrder, isSpinLocks, blockingTimeout, timeSource, statementMetadata, statementUserObject, statementContext, isFailed, nameProvided);
                break;
            default:
                statement = new EPStatementImpl(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, preserveDispatchOrder, isSpinLocks, blockingTimeout, timeSource, statementMetadata, statementUserObject, statementContext, isFailed, nameProvided);
        }
        return statement;
    }

    private EPStatementSPI getSchemaEPS(String expressionNoAnnotations, boolean isPattern, DispatchService dispatchService, StatementLifecycleSvcImpl statementLifecycleSvc, long timeLastStateChange, boolean preserveDispatchOrder, boolean isSpinLocks, long blockingTimeout, TimeSourceService timeSource, StatementMetadata statementMetadata, Object statementUserObject, StatementContext statementContext, boolean isFailed, boolean nameProvided) {
        return new EPStatementImpl(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, preserveDispatchOrder, isSpinLocks, blockingTimeout, timeSource, statementMetadata, statementUserObject, statementContext, isFailed, nameProvided);
    }


    private EPStatementSPI getWindowOperator(String expressionNoAnnotations, boolean isPattern, DispatchService dispatchService, StatementLifecycleSvcImpl statementLifecycleSvc, long timeLastStateChange, boolean preserveDispatchOrder, boolean isSpinLocks, long blockingTimeout, TimeSourceService timeSource, StatementMetadata statementMetadata, Object statementUserObject, StatementContext statementContext, boolean isFailed, boolean nameProvided) {
        return new WindowOperatorEPSImpl(expressionNoAnnotations, isPattern, dispatchService, statementLifecycleSvc, timeLastStateChange, preserveDispatchOrder, isSpinLocks, blockingTimeout, timeSource, statementMetadata, statementUserObject, statementContext, isFailed, nameProvided);
    }

    public StopCallback makeStopMethod(StatementAgentInstanceFactoryResult startResult) {
        return startResult.getStopCallback();
    }
}

