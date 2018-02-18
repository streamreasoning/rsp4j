package it.polimi.spe.report.strategies;

import it.polimi.spe.content.Content;
import it.polimi.spe.windowing.Window;
import lombok.Setter;

import java.util.Map;

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
    public boolean match(Window w, long tapp, long tsys) {
        return tapp % period == 0;
    }

    @Override
    public void setActiveWindows(Map<Window, Content> active_windows) {

    }

}
