package it.polimi.spe.report.strategies;


import it.polimi.spe.windowing.Window;
import it.polimi.spe.content.Content;

import java.util.Map;

/**
 *
 * According to Botan et al.
 * SPEs use different reporting
 * strategies to define their reporting policy.
 *
 * **/
public interface ReportingStrategy {
    boolean match(Window w, long tapp, long tsys);

    void setActiveWindows(Map<Window, Content> active_windows);
}


