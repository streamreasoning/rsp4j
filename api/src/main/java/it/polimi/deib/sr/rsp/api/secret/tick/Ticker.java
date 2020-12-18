package it.polimi.deib.sr.rsp.api.secret.tick;

import it.polimi.deib.sr.rsp.api.operators.s2r.execution.instance.Window;

public interface Ticker {
    void tick(long t_e, Window w);

}
