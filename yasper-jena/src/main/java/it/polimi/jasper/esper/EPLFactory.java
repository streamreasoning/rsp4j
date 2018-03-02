package it.polimi.jasper.esper;

import com.espertech.esper.client.soda.*;
import it.polimi.jasper.parser.streams.WindowOperatorNode;
import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.enums.WindowType;
import it.polimi.yasper.core.utils.EncodingUtils;
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

    public static EPStatementObjectModel toEPL(WindowOperatorNode wof, Stream s) {
        return toEPL(wof, s.getURI());
    }

    public static EPStatementObjectModel toEPL(WindowOperatorNode wof, String s) {
        EPStatementObjectModel stmt = new EPStatementObjectModel();

        stmt.setAnnotations(getAnnotations(wof, s));
        SelectClause selectClause = SelectClause.create().addWildcard();
        stmt.setSelectClause(selectClause);
        FromClause fromClause = FromClause.create();
        FilterStream stream = FilterStream.create(EncodingUtils.encode(s));
        stream.addView(getWindow(wof));
        fromClause.add(stream);
        stmt.setFromClause(fromClause);

        OutputLimitClause outputLimitClause;

        if (WindowType.Physical.equals(wof.getType())) {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.SNAPSHOT, wof.getStep());
        } else {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.SNAPSHOT, getTimePeriod(wof.getStep(), wof.getUnitStep()));
        }

        stmt.setOutputLimitClause(outputLimitClause);
        return stmt;
    }


    public static EPStatementObjectModel toEPL(int step, String unitStep, WindowType type, String s, View window, List<AnnotationPart> annotations) {
        EPStatementObjectModel stmt = new EPStatementObjectModel();

        stmt.setAnnotations(annotations);
        SelectClause selectClause = SelectClause.create().addWildcard();
        stmt.setSelectClause(selectClause);
        FromClause fromClause = FromClause.create();
        FilterStream stream = FilterStream.create(EncodingUtils.encode(s));
        stream.addView(window);
        fromClause.add(stream);
        stmt.setFromClause(fromClause);

        OutputLimitClause outputLimitClause;

        if (WindowType.Physical.equals(type)) {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.SNAPSHOT, step);
        } else {
            TimePeriodExpression timePeriod = getTimePeriod(step, unitStep);
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.SNAPSHOT, timePeriod);
        }

        stmt.setOutputLimitClause(outputLimitClause);
        return stmt;
    }

    public static EPStatementObjectModel toIREPL(WindowOperatorNode wof, String s) {
        EPStatementObjectModel stmt = new EPStatementObjectModel();

        stmt.setAnnotations(getAnnotations(wof, s));

        SelectClause selectClause = SelectClause.create().addWildcard();
        selectClause.setStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH);
        stmt.setSelectClause(selectClause);
        FromClause fromClause = FromClause.create();
        FilterStream stream = FilterStream.create(EncodingUtils.encode(s));
        stream.addView(getWindow(wof));
        fromClause.add(stream);
        stmt.setFromClause(fromClause);

        OutputLimitClause outputLimitClause;

        if (WindowType.Physical.equals(wof.getType())) {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.DEFAULT, wof.getStep());
        } else {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.DEFAULT, getTimePeriod(wof.getStep(), wof.getUnitStep()));
        }

        stmt.setOutputLimitClause(outputLimitClause);
        return stmt;
    }


    public static EPStatementObjectModel toIREPL(WindowOperatorNode wof, Stream s) {
        return toIREPL(wof, s.getURI());
    }

    private static List<AnnotationPart> getAnnotations(WindowOperatorNode wof, Stream s) {
        return getAnnotations(wof, s.getURI());
    }

    private static List<AnnotationPart> getAnnotations(WindowOperatorNode wof, String s) {
        AnnotationPart name = new AnnotationPart();
        name.setName("Name");
        name.addValue(EncodingUtils.encode(wof.getName()));

        AnnotationPart range = new AnnotationPart();
        range.setName("Tag");
        range.addValue("name", "range");
        range.addValue("value", wof.getRange() + "");

        AnnotationPart slide = new AnnotationPart();
        slide.setName("Tag");
        slide.addValue("name", "step");
        slide.addValue("value", wof.getStep() + "");

        AnnotationPart stream_uri = new AnnotationPart();
        stream_uri.setName("Tag");
        stream_uri.addValue("name", "stream");
        stream_uri.addValue("value", (EncodingUtils.encode(s)));

        return Arrays.asList(name, stream_uri, range, slide);
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

    public static View getWindow(WindowOperatorNode wof) {
        View view;
        ArrayList<Expression> parameters = new ArrayList<Expression>();
        if (WindowType.Physical.equals(wof.getType())) {

            parameters.add(Expressions.constant(wof.getStep()));
            view = View.create("win", "length", parameters);

        } else {
            parameters.add(getTimePeriod(wof.getRange(), wof.getUnitRange()));
            view = View.create("win", "time", parameters);
        }
        return view;
    }

    public static View getWindow(int range, String unitRange, WindowType type) {
        View view;
        ArrayList<Expression> parameters = new ArrayList<Expression>();
        if (WindowType.Physical.equals(type)) {
            parameters.add(Expressions.constant(range));
            view = View.create("win", "length", parameters);
        } else {
            parameters.add(getTimePeriod(range, unitRange));
            view = View.create("win", "time", parameters);
        }
        return view;
    }

    private static TimePeriodExpression getTimePeriod(Integer omega, String unit_omega) {
        if ("ms".equals(unit_omega)) {
            return Expressions.timePeriod(null, null, null, null, omega);
        } else if ("s".equals(unit_omega)) {
            return Expressions.timePeriod(null, null, null, omega, null);
        } else if ("m".equals(unit_omega)) {
            return Expressions.timePeriod(null, null, omega, null, null);
        } else if ("h".equals(unit_omega)) {
            return Expressions.timePeriod(null, omega, null, null, null);
        } else if ("d".equals(unit_omega)) {
            return Expressions.timePeriod(omega, null, null, null, null);
        }
        return null;
    }

    public static String toEPLSchema(Stream s) {
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


}
