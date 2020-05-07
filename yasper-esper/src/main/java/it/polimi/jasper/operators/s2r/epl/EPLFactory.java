package it.polimi.jasper.operators.s2r.epl;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import it.polimi.jasper.secret.report.EsperCCReportStrategy;
import it.polimi.jasper.secret.report.EsperWCReportStrategy;
import it.polimi.jasper.utils.EncodingUtils;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.syntax.WindowType;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.extern.log4j.Log4j;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by riccardo on 04/09/2017.
 */
@Log4j
public class EPLFactory {

    public static EPStatementObjectModel toEPL(Tick tick, Report report, Maintenance maintenance, long step, String unitStep, WindowType type, String s, View window, List<AnnotationPart> annotations) {
        EPStatementObjectModel stmt = new EPStatementObjectModel();

        stmt.setAnnotations(annotations);

        //MAINTENANCE ISTREAM/DSTREAM

        if (Maintenance.NAIVE.equals(maintenance)) {
            SelectClause selectClause1 = SelectClause.create();
            SelectClause selectClause = selectClause1.addWildcard();
            stmt.setSelectClause(selectClause);
        } else {
            SelectClause selectClause1 = SelectClause.createWildcard(StreamSelector.RSTREAM_ISTREAM_BOTH);
            stmt.setSelectClause(selectClause1);
        }

        OutputLimitClause outputLimitClause;
        OutputLimitSelector snapshot = OutputLimitSelector.ALL;

        if (Arrays.stream(report.strategies()).anyMatch(rs -> rs instanceof EsperWCReportStrategy)) {
            TimePeriodExpression timePeriod = getTimePeriod((int) step, unitStep);
            outputLimitClause = OutputLimitClause.create(snapshot, timePeriod);
            stmt.setOutputLimitClause(outputLimitClause);
        } else if (Arrays.stream(report.strategies()).anyMatch(rs -> rs instanceof EsperCCReportStrategy)) {

            //Default Esper
        }

        FromClause fromClause = FromClause.create();
        FilterStream stream = FilterStream.create(EncodingUtils.encode(s));
        stream.addView(window);
        fromClause.add(stream);
        stmt.setFromClause(fromClause);


        //SETTING TICK
        OutputLimitUnit events = OutputLimitUnit.EVENTS;
//
//        if (Tick.TIME_DRIVEN.equals(tick)) {
//            TimePeriodExpression timePeriod = getTimePeriod((int) step, unitStep);
//            outputLimitClause = OutputLimitClause.create(snapshot, timePeriod);
//            stmt.setOutputLimitClause(outputLimitClause);
//        } else if (Tick.BATCH_DRIVEN.equals(tick)) {
//            outputLimitClause = new OutputLimitClause(snapshot, (double) step);
//            stmt.setOutputLimitClause(outputLimitClause);
//        } else if (Tick.TUPLE_DRIVEN.equals(tick)) {
//            outputLimitClause = new OutputLimitClause(snapshot, 1D);
//            stmt.setOutputLimitClause(outputLimitClause);
//        }

        return stmt;
    }


    public static List<AnnotationPart> getAnnotations(String name1, int range1, int step1, String s) {
        AnnotationPart name = new AnnotationPart();
        name.setName("Name");
        name.addValue(EncodingUtils.encode(name1));

        AnnotationPart range = new AnnotationPart();
        range.setName("Tag");
        range.addValue("name", "range");
        range.addValue("value", range1 + "");

        AnnotationPart slide = new AnnotationPart();
        slide.setName("Tag");
        slide.addValue("name", "step");
        slide.addValue("value", step1 + "");

        AnnotationPart stream_uri = new AnnotationPart();
        stream_uri.setName("Tag");
        stream_uri.addValue("name", "stream");
        stream_uri.addValue("value", (EncodingUtils.encode(s)));

        return Arrays.asList(name, stream_uri, range, slide);
    }


    public static View getWindow(long range, String unitRange, WindowType type) {
        View view;
        ArrayList<Expression> parameters = new ArrayList<>();
        if (WindowType.Physical.equals(type)) {
            parameters.add(Expressions.constant(range));
            view = View.create("win", "length", parameters);
        } else {
            parameters.add(getTimePeriod((int) range, unitRange));
            view = View.create("win", "time", parameters);
        }
        return view;
    }

    private static TimePeriodExpression getTimePeriod(Integer omega, String unit_omega) {
        String unit = unit_omega.toLowerCase();
        if ("ms".equals(unit) || "millis".equals(unit) || "milliseconds".equals(unit)) {
            return Expressions.timePeriod(null, null, null, null, omega);
        } else if ("s".equals(unit) || "seconds".equals(unit) || "sec".equals(unit)) {
            return Expressions.timePeriod(null, null, null, omega, null);
        } else if ("m".equals(unit) || "minutes".equals(unit) || "min".equals(unit)) {
            return Expressions.timePeriod(null, null, omega, null, null);
        } else if ("h".equals(unit) || "hours".equals(unit) || "hour".equals(unit)) {
            return Expressions.timePeriod(null, omega, null, null, null);
        } else if ("d".equals(unit) || "days".equals(unit)) {
            return Expressions.timePeriod(omega, null, null, null, null);
        }
        return null;
    }

    public static String toEPLSchema(WebStream s) {
        CreateSchemaClause schema = new CreateSchemaClause();
        schema.setSchemaName(EncodingUtils.encode(s.getURI()));
        schema.setInherits(new HashSet<String>(Arrays.asList(new String[]{"TStream"})));
        List<SchemaColumnDesc> columns = Arrays.asList(
                new SchemaColumnDesc("sys_timestamp", "long", false),
                new SchemaColumnDesc("app_timestamp", "long", false),
                new SchemaColumnDesc("content", Object.class.getTypeName(), false));
        schema.setColumns(columns);
        StringWriter writer = new StringWriter();
        schema.toEPL(writer);
        return writer.toString();
    }

    public static EPStatement getWindowAssigner(Tick tick, Maintenance maintenance, Report report, boolean time, String name, long step, long range, String unitStep, String unitRange, WindowType type, Time time1) {
        List<AnnotationPart> annotations = new ArrayList<>();//EPLFactory.getAnnotations(name, range, step, name);
        View window = EPLFactory.getWindow((int) range, unitRange, type);
        EPStatementObjectModel epStatementObjectModel = EPLFactory.toEPL(tick, report, maintenance, step, unitStep, type, name, window, annotations);
        log.info(epStatementObjectModel.toEPL());
        return RuntimeManager.getAdmin().create(epStatementObjectModel, name);
    }


    public static EPStatement getWindowAssignerTB(Tick tick, Maintenance maintenance, Report report, boolean time, String name, long step, long range, String unitStep, String unitRange, WindowType type, Time time1) {
        List<AnnotationPart> annotations = new ArrayList<>();//EPLFactory.getAnnotations(name, range, step, name);
        View window = EPLFactory.getWindow((int) range, unitRange, type);
        EPStatementObjectModel epStatementObjectModel = EPLFactory.toEPL(tick, report, Maintenance.NAIVE, step, unitStep, type, name, window, annotations);
        log.info(epStatementObjectModel.toEPL());
        return RuntimeManager.getAdmin().create(epStatementObjectModel, name);
    }


}
