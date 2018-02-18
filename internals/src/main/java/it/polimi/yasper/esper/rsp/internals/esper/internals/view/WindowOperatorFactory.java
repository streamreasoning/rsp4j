package it.polimi.yasper.esper.rsp.internals.esper.internals.view;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.TimeWindow;
import com.espertech.esper.collection.ViewUpdatedCollection;
import com.espertech.esper.core.context.util.AgentInstanceContext;
import com.espertech.esper.core.context.util.AgentInstanceViewFactoryChainContext;
import com.espertech.esper.core.service.EPStatementSPI;
import com.espertech.esper.core.service.StatementContext;
import com.espertech.esper.epl.expression.core.ExprNode;
import com.espertech.esper.epl.expression.time.ExprTimePeriodEvalDeltaConst;
import com.espertech.esper.epl.expression.time.ExprTimePeriodEvalDeltaConstFactory;
import com.espertech.esper.view.*;
import com.espertech.esper.view.window.RandomAccessByIndexGetter;
import com.espertech.esper.view.window.TimeWindowView;
import it.polimi.rspql.cql.s2_.WindowOperatorNode;
import it.polimi.yasper.esper.rsp.internals.esper.internals.WindowOperatorEPSImpl;

import java.util.List;

/**
 * Created by riccardo on 01/09/2017.
 */
public class WindowOperatorFactory implements DataWindowViewFactory, DataWindowViewWithPrevious {
    protected ExprTimePeriodEvalDeltaConstFactory timeDeltaComputationFactory;

    private EventType eventType;

    //TODO version with mappings

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException {
        if (expressionParameters.size() != 1) {
            throw new ViewParameterException(getViewParamMessage());
        }

        timeDeltaComputationFactory = ViewFactoryTimePeriodHelper.validateAndEvaluateTimeDeltaFactory(getViewName(), viewFactoryContext.getStatementContext(), expressionParameters.get(0), getViewParamMessage(), 0);
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException {
        this.eventType = parentEventType;
    }

    public Object makePreviousGetter() {
        return new RandomAccessByIndexGetter();
    }

    public View makeView(AgentInstanceViewFactoryChainContext agentInstanceViewFactoryContext) {
        ExprTimePeriodEvalDeltaConst timeDeltaComputation = timeDeltaComputationFactory.make(getViewName(), "view", agentInstanceViewFactoryContext.getAgentInstanceContext());
        StatementContext statementContext1 = agentInstanceViewFactoryContext.getStatementContext();
        EPStatementSPI statement = statementContext1.getStatement();
        TimeWindow timeWindow = new TimeWindow(agentInstanceViewFactoryContext.isRemoveStream());
        if (statement instanceof WindowOperatorNode) {
            WindowOperatorEPSImpl wo = (WindowOperatorEPSImpl) statement;
            wo.setWindow(timeWindow);
        }
        ViewUpdatedCollection randomAccess = statementContext1.getViewServicePreviousFactory().getOptPreviousExprRandomAccess(agentInstanceViewFactoryContext);
        TimeVaryingItemView eventBeans = new TimeVaryingItemView(agentInstanceViewFactoryContext, this, timeDeltaComputation, randomAccess, timeWindow);


        return eventBeans;
    }

    public EventType getEventType() {
        return eventType;
    }

    public boolean canReuse(View view, AgentInstanceContext agentInstanceContext) {
        if (!(view instanceof TimeWindowView)) {
            return false;
        }

        TimeWindowView myView = (TimeWindowView) view;
        ExprTimePeriodEvalDeltaConst delta = timeDeltaComputationFactory.make(getViewName(), "view", agentInstanceContext);
        if (!delta.equalsTimePeriod(myView.getTimeDeltaComputation())) {
            return false;
        }

        // For reuse of the time window it doesn't matter if it provides random access or not
        return myView.isEmpty();
    }

    public String getViewName() {
        return "Time";
    }

    public ExprTimePeriodEvalDeltaConstFactory getTimeDeltaComputationFactory() {
        return timeDeltaComputationFactory;
    }

    private String getViewParamMessage() {
        return getViewName() + " view requires a single numeric or time period parameter";
    }

}