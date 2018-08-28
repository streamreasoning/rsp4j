package it.polimi.yasper.core.spe.tick;

import it.polimi.yasper.core.spe.operators.s2r.execution.instance.Window;

public interface Ticker {
    void tick(long t_e, Window w);

}
