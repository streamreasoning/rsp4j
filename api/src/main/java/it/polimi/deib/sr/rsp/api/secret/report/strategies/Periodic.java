package it.polimi.deib.sr.rsp.api.secret.report.strategies;

import it.polimi.deib.sr.rsp.api.secret.content.Content;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.instance.Window;
import lombok.Setter;

/**
 * Periodic (Rpr): reporting is done for t only
 * if it is a multiple of x, where x denotes the reporting frequency.
 **/

//TODO returns true independently from w, but it
// communicates directly with the system clock to decide
// whether it is time to report
public class Periodic implements ReportingStrategy {
    @Setter
    private long period;

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return tapp % period == 0;
    }

}
