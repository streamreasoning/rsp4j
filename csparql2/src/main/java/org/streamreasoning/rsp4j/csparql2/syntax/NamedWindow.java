package org.streamreasoning.rsp4j.csparql2.syntax;


import lombok.Data;
import org.apache.jena.graph.Node;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowType;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
public class NamedWindow implements WindowNode {
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
    public long getT0() {
        return 0;
    }

    @Override
    public long getRange() {
        return WindowType.Logical.equals(getType()) ? logicalRange.toMillis() : physicalRange;
    }

    @Override
    public long getStep() {
        return WindowType.Logical.equals(getType()) ? logicalStep.toMillis() : physicalStep;
    }

    @Override
    public String getUnitRange() {
        return WindowType.Logical.equals(getType()) ? ChronoUnit.MILLIS.toString().toLowerCase() : "TRIPLE";
    }

    @Override
    public String getUnitStep() {
        return WindowType.Logical.equals(getType()) ? ChronoUnit.MILLIS.toString().toLowerCase() : "TRIPLE";
    }

    @Override
    public String iri() {
        return windowUri.getURI();
    }

    @Override
    public boolean named() {
        return windowUri != null;
    }
}
