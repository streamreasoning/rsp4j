package it.polimi.spe.report.strategies;


import it.polimi.spe.content.Content;
import it.polimi.spe.windowing.Window;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Non-empty content (Rne): reporting is done
 * for t only if the content at t is not empty.
 *
 * **/
public class NonEmptyContent implements ReportingStrategy {
    private Map<Window, Content> active_windows = new HashMap<>();

    @Override
    public boolean match(Window w, long tapp, long tsys) {
        return active_windows.get(w).size() > 0;
    }

    @Override
    public void setActiveWindows(Map<Window, Content> active_windows) {
        this.active_windows = active_windows;
    }
}
