package neo4j.poc;

import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.operators.s2r.syntax.WindowType;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TestWindowNode implements WindowNode {
    @Override
    public String iri() {
        return "w1";
    }

    @Override
    public boolean named() {
        return true;
    }

    @Override
    public WindowType getType() {
        return WindowType.TIME_BASED;
    }

    @Override
    public long getT0() {
        return 0L;
    }

    @Override
    public long getRange() {
        return Duration.ofSeconds(10).toMillis();
    }

    @Override
    public long getStep() {
        return Duration.ofSeconds(10).toMillis();
    }

    @Override
    public String getUnitRange() {
        return ChronoUnit.MILLIS.toString().toLowerCase();
    }

    @Override
    public String getUnitStep() {
        return ChronoUnit.MILLIS.toString().toLowerCase();
    }
}
