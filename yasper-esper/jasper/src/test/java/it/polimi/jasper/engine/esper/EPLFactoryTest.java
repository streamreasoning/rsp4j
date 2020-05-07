package it.polimi.jasper.engine.esper;

import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.soda.View;
import it.polimi.jasper.operators.s2r.epl.EPLFactory;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.syntax.WindowType;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.NonEmptyContent;
import it.polimi.yasper.core.secret.report.strategies.OnContentChange;
import it.polimi.yasper.core.secret.report.strategies.OnWindowClose;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EPLFactoryTest {

    private String stream = "teststream";
    private Maintenance naive = Maintenance.NAIVE;
    private Maintenance incremental = Maintenance.INCREMENTAL;
    private Tick timeDriven = Tick.TIME_DRIVEN;
    private Tick batchDriven = Tick.BATCH_DRIVEN;
    private Tick tupleDriven = Tick.TUPLE_DRIVEN;

    private WindowType logical = WindowType.Logical;
    private WindowType physical = WindowType.Physical;


    @Test
    public void timeWindow() {
        String unit = "Seconds";
        long step = 5;
        View window = EPLFactory.getWindow(step, unit, logical);
        StringWriter writer = new StringWriter();
        window.toEPL(writer);

        assertEquals("win:time(5 seconds)", writer.toString());
    }

    @Test
    public void tupleWindow() {
        String unit = "Seconds";
        long step = 5;
        View window = EPLFactory.getWindow(step, "events'", physical);
        StringWriter writer = new StringWriter();
        window.toEPL(writer);

        assertEquals("win:length(5L)", writer.toString());
    }

    @Test
    public void csparqlT5() {

        //TUMBLING 5 seconds

        Report report = new ReportImpl();
        report.add(new OnWindowClose());
        report.add(new NonEmptyContent());

        String unit = "Seconds";
        long step = 5;
        View window = EPLFactory.getWindow(step, unit, logical);

        EPStatementObjectModel epStatementObjectModel = EPLFactory.toEPL(timeDriven, report, naive, step, unit, logical, stream, window, Collections.EMPTY_LIST);

        System.out.println(epStatementObjectModel.toEPL());

        assertEquals("select * from " + stream + ".win:time(5 seconds) output all every 5 seconds", epStatementObjectModel.toEPL());
    }

    @Test
    public void cqels5() {

        //TUMBLING 5 seconds

        Report report = new ReportImpl();
        report.add(new OnContentChange());

        String unit = "Seconds";
        long step = 5;
        View window = EPLFactory.getWindow(step, unit, logical);

        EPStatementObjectModel epStatementObjectModel = EPLFactory.toEPL(tupleDriven, report, naive, step, unit, logical, stream, window, Collections.EMPTY_LIST);

        System.out.println(epStatementObjectModel.toEPL());

        assertEquals("select * from " + stream + ".win:time(5 seconds) output all every 1 events", epStatementObjectModel.toEPL());
    }

}
