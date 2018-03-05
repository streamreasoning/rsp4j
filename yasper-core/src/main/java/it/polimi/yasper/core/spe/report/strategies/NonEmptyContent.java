package it.polimi.yasper.core.spe.report.strategies;


import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.definition.Window;
import it.polimi.yasper.core.spe.content.EmptyContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Non-empty content (Rne): reporting is done
 * for t only if the content at t is not empty.
 **/
public class NonEmptyContent implements ReportingStrategy {
    private Map<Window, Content> active_windows = new HashMap<>();

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return c.size() > 0 || (c instanceof EmptyContent);
    }

    @Override
    public void setActiveWindows(Map<Window, Content> active_windows) {
        this.active_windows = active_windows;
    }
}
