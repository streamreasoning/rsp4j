package org.streamreasoning.rsp4j.esper.engine.esper;

import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.soda.View;
import org.streamreasoning.rsp4j.esper.operators.s2r.epl.EPLFactory;

import org.junit.Test;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowType;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.NonEmptyContent;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnContentChange;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;

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
