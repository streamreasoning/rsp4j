package it.polimi.jasper.parser.streams;

import com.espertech.esper.client.soda.*;
import it.polimi.yasper.core.utils.EncodingUtils;
import lombok.*;
import org.apache.jena.graph.Node_URI;

import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Riccardo on 12/08/16.
 */
@Data
@NoArgsConstructor
@ToString(exclude = {"regex", "p"})
@RequiredArgsConstructor
public class Window {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    final private String regex = "([0-9]+)\\s*(ms|s|m|h|d|GRAPH|TRIPLES)";
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    final private Pattern p = Pattern.compile(regex);
    @NonNull
    private Node_URI iri;
    private Integer beta;
    private Integer omega;
    private String unit_omega;
    private String unit_beta;
    private Stream stream;
    private WindowType type = WindowType.Logical;
    private View window;

    public Window addConstrain(String match) {
        // TODO hide visibility out of the package
        Matcher matcher = p.matcher(match);
        if (matcher.find()) {
            MatchResult res = matcher.toMatchResult();
            this.beta = this.omega = Integer.parseInt(res.group(1));
            this.unit_beta = this.unit_omega = res.group(2);
            if ("GRAPH".equals(unit_omega) || "TRIPLE".equals(unit_omega)) {
                this.type = WindowType.Physical;
            }

        }
        return this;
    }

    public Window addSlide(String match) {
        // TODO hide visibility out of the package
        Matcher matcher = p.matcher(match);
        if (matcher.find()) {
            MatchResult res = matcher.toMatchResult();
            this.beta = Integer.parseInt(res.group(1));
            this.unit_beta = res.group(2);
            if ("GRAPH".equals(unit_beta) || "TRIPLE".equals(unit_beta)) {
                this.type = WindowType.Physical;
            }
        }
        return this;
    }

    public Window addStreamUri(Node_URI uri) {
        // TODO hide visibility out of the package
        if (stream == null) {
            stream = new Stream(uri);
        }
        stream.setIri(uri);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Window window = (Window) o;

        if ((isNamed() && !window.isNamed()) || (!isNamed() && window.isNamed())) {
            return false;
        }

        if (isNamed() && window.isNamed()) {
            return iri.equals(window.getIri());
        }

        if (!isNamed() && !window.isNamed()) {
            return stream.equals(window.getStream());
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = iri != null ? iri.hashCode() : 0;
        result = 31 * result + (beta != null ? beta.hashCode() : 0);
        result = 31 * result + (omega != null ? omega.hashCode() : 0);
        result = 31 * result + (unit_omega != null ? unit_omega.hashCode() : 0);
        result = 31 * result + (unit_beta != null ? unit_beta.hashCode() : 0);
        result = 31 * result + (stream != null ? stream.hashCode() : 0);
        return result;
    }

    public View getWindow() {
        View view;
        ArrayList<Expression> parameters = new ArrayList<Expression>();
        if (WindowType.Physical.equals(type)) {

            parameters.add(Expressions.constant(omega));
            view = View.create("win", "length", parameters);

        } else {
            parameters.add(getTimePeriod(omega, unit_omega));
            view = View.create("win", "time", parameters);
        }
        return view;
    }

    private TimePeriodExpression getTimePeriod(Integer omega, String unit_omega) {
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

    public boolean isNamed() {
        return iri != null;
    }

    public EPStatementObjectModel toEPL() {
        EPStatementObjectModel stmt = new EPStatementObjectModel();
        SelectClause wildcard = SelectClause.createWildcard();
        stmt.setSelectClause(wildcard);
        FromClause fromClause = FromClause.create();
        FilterStream stream = FilterStream.create(EncodingUtils.encode(this.stream.getIri().getURI()));
        stream.addView(getWindow());
        fromClause.add(stream);
        stmt.setFromClause(fromClause);

        OutputLimitClause outputLimitClause;

        if (WindowType.Physical.equals(type)) {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.SNAPSHOT, beta);
        } else {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.SNAPSHOT, getTimePeriod(beta, unit_beta));
        }

        stmt.setOutputLimitClause(outputLimitClause);
        return stmt;
    }

    public EPStatementObjectModel toIREPL() {
        EPStatementObjectModel stmt = new EPStatementObjectModel();
        SelectClause selectClause = SelectClause.createWildcard();
        selectClause.setStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH);
        stmt.setSelectClause(selectClause);
        FromClause fromClause = FromClause.create();
        FilterStream stream = FilterStream.create(EncodingUtils.encode(this.stream.getIri().getURI()));
        stream.addView(getWindow());
        fromClause.add(stream);
        stmt.setFromClause(fromClause);

        OutputLimitClause outputLimitClause;

        if (WindowType.Physical.equals(type)) {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.DEFAULT, beta);
        } else {
            outputLimitClause = OutputLimitClause.create(OutputLimitSelector.DEFAULT, getTimePeriod(beta, unit_beta));
        }

        stmt.setOutputLimitClause(outputLimitClause);
        return stmt;
    }

    public String getStreamURI() {
        return stream.getIri().getURI();
    }

    public enum WindowType {
        Logical, Physical;
    }

}
