package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowType;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@AllArgsConstructor
public class WindowNodeImpl implements WindowNode {

    private final IRI windowUri;
    private final Duration logicalRange;
    private Duration logicalStep;
    private final Integer t0;


    @Override
    public WindowType getType() {
        return WindowType.Logical;
    }

    @Override
    public long getT0() {
        return t0;
    }

    @Override
    public long getRange() {
        return logicalRange.getSeconds() * 1000;
    }

    @Override
    public long getStep() {
        return logicalStep != null ? logicalStep.getSeconds() * 1000 : -1;
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
    public String iri() {
        return windowUri.getIRIString();
    }

    @Override
    public boolean named() {
        return windowUri != null;
    }
}
