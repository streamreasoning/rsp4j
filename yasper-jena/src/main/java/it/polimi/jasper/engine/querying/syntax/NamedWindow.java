package it.polimi.jasper.engine.querying.syntax;

import it.polimi.jasper.engine.spe.esper.EPLFactory;
import it.polimi.yasper.core.enums.WindowType;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;
import lombok.Data;
import org.apache.jena.graph.Node;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
public class NamedWindow implements WindowOperatorNode {
    public static int LOGICAL_WINDOW = 0;
    public static int PHYSICAL_WINDOW = 1;
    public WindowType windowType;
    private Node windowUri;
    private Node streamUri;
    private Duration logicalRange;
    private Duration logicalStep;
    private Integer physicalRange;
    private Integer physicalStep;
    private RSPQLJenaQuery query;

    public NamedWindow(RSPQLJenaQuery query, Node windowUri, Node streamUri, int windowType) {
        this.query = query;
        this.windowUri = windowUri;
        this.streamUri = streamUri;
        this.windowType = WindowType.valueOf(windowType);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FROM NAMED WINDOW ");
        String w = windowUri.toString();
        String wShort = query.getPrefixMapping().qnameFor(w);
        if (wShort != null) {
            sb.append(String.format("%s ", wShort));
        } else {
            sb.append(String.format("<%s> ", w));
        }
        String s = streamUri.toString();
        String sShort = query.getPrefixMapping().qnameFor(s);
        if (sShort != null) {
            sb.append(String.format("%s ", sShort));
        } else {
            sb.append(String.format("<%s> ", s));
        }

        return sb.toString();
    }

    @Override
    public WindowType getType() {
        return windowType;
    }

    @Override
    public int getT0() {
        return 0;
    }

    @Override
    public int getRange() {
        return WindowType.Logical.equals(getType()) ? (int) logicalRange.getSeconds() : physicalRange;
    }

    @Override
    public int getStep() {
        return WindowType.Logical.equals(getType()) ? (int) logicalStep.getSeconds() : physicalStep;
    }

    @Override
    public String getUnitRange() {
        return WindowType.Logical.equals(getType()) ? ChronoUnit.SECONDS.toString() : "TRIPLE";
    }

    @Override
    public String getUnitStep() {
        return WindowType.Logical.equals(getType()) ? ChronoUnit.SECONDS.toString() : "TRIPLE";
    }

    @Override
    public String getName() {
        return windowUri.getURI();
    }

    @Override
    public boolean isNamed() {
        return windowUri != null;
    }

    @Override
    public WindowAssigner apply(Stream s) {
        return EPLFactory.getWindowAssigner(query.getResolver().resolveToString("streams/" + streamUri.getURI()), getStep(), getRange(), getUnitStep(), getUnitRange(), windowType);
    }

}
