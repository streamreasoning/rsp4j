package org.streamreasoning.rsp4j.esper.operators.s2r;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import org.streamreasoning.rsp4j.esper.operators.s2r.epl.EPLFactory;
import org.streamreasoning.rsp4j.esper.operators.s2r.epl.RuntimeManager;
import org.streamreasoning.rsp4j.esper.utils.EncodingUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;

import java.util.Observer;

@Log4j
@Getter
public abstract class AbstractEsperWindowAssigner<I, O> implements StreamToRelationOp<I, O>, Observer {

    protected final String name;
    protected final boolean eventtime;
    protected EPAdministrator admin;
    protected EPStatement statement;
    protected EPRuntime runtime;
    protected Time time;
    protected Report report;
    protected Tick tick;
    protected ReportGrain reportGrain = ReportGrain.SINGLE;

    public AbstractEsperWindowAssigner(String stream, Tick tick, Maintenance m, Report report, boolean event_time, Time time, WindowNode wo) {
        this.name = EncodingUtils.encode(stream);
        this.tick = tick;
        this.report = report;
        this.eventtime = event_time;
        this.runtime = RuntimeManager.getEPRuntime();
        this.statement = EPLFactory.getWindowAssigner(tick, m, report, eventtime, stream, wo.getStep(), wo.getRange(), wo.getUnitStep(), wo.getUnitRange(), wo.getType(), time);
        this.time = time;
    }

    @Override
    public Report report() {
        return report;
    }

    @Override
    public Tick tick() {
        return tick;
    }

    @Override
    public Time time() {
        return time;
    }

    @Override
    public String iri() {
        return name;
    }


}
