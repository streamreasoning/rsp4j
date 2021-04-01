package org.streamreasoning.rsp4j.api.secret.report.strategies;


import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;

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
