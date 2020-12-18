package it.polimi.yasper.core.secret.tick;

import it.polimi.yasper.core.operators.s2r.execution.instance.Window;

public interface Ticker {
    void tick(long t_e, Window w);

}
