package it.polimi.jasper.operators.s2r;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import it.polimi.jasper.operators.s2r.epl.EPLFactory;
import it.polimi.jasper.operators.s2r.epl.RuntimeManager;
import it.polimi.jasper.utils.EncodingUtils;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Observer;

@Log4j
@Getter
public abstract class AbstractEsperWindowAssigner<I, O> implements Assigner<I, O>, Observer {

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

    @Override
    public boolean named() {
        return name != null;
    }

}
