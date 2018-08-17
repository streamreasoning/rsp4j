package it.polimi.yasper.core.spe.report.strategies;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.definition.Window;

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
