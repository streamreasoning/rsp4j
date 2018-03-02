package it.polimi.yasper.core.spe.report.strategies;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.Window;

import java.util.Map;

/**
 *
 *  Window close (Rwc): reporting is done for t
 *  only when the active window closes (i.e., |Scope(t)| = w ).
 *
 * **/
public class OnWindowClose implements ReportingStrategy {

    @Override
    public boolean match(Window w, long tapp, long tsys) {
        return w.getC() < tapp;
    }

    @Override
    public void setActiveWindows(Map<Window, Content> active_windows) {
    }
}
