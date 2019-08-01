package it.polimi.yasper.core.secret.report.strategies;

import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;

/**
 * Window close (Rwc): reporting is done for t
 * only when the active window closes (i.e., |Scope(t)| = w ).
 **/
public class OnWindowClose implements ReportingStrategy {

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return w.getC() < tapp;
    }

}
