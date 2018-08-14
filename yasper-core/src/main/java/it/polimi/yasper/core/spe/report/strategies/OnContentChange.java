package it.polimi.yasper.core.spe.report.strategies;


import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.definition.Window;

/**
 * Content change (Rcc): reporting is done for t only
 * if the content has changed since t - 1.
 **/
public class OnContentChange implements ReportingStrategy {

    private Content last_content;

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        if (last_content == null) {
            last_content = c;
            return true;
        }

        return !last_content.equals(c);
    }
}
