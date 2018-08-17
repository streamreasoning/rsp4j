package simple.windowing;

import it.polimi.yasper.core.enums.WindowType;
import it.polimi.yasper.core.quering.rspql.window.WindowNode;
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
    public String getName() {
        return windowUri.getIRIString();
    }

    @Override
    public boolean isNamed() {
        return windowUri != null;
    }
}
