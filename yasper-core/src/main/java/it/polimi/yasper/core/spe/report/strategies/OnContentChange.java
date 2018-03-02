package it.polimi.yasper.core.spe.report.strategies;


import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.Window;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Content change (Rcc): reporting is done for t only
 * if the content has changed since t - 1.
 *
 * **/
public class OnContentChange implements ReportingStrategy {

    private Map<Window, Long> last_change = new HashMap<>();
    private Map<Window, Content> active_windows = new HashMap<>();

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        last_change.computeIfAbsent(w, k -> active_windows.get(w).getTimeStampLastUpdate());
        if (last_change.containsKey(w))
            return last_change.get(w) != active_windows.get(w).getTimeStampLastUpdate();
        else
            return true;
    }

    @Override
    public void setActiveWindows(Map<Window, Content> active_windows) {
        this.active_windows = active_windows;
    }
}
