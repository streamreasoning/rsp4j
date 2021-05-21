package org.streamreasoning.rsp4j.api.secret.report.strategies;


import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;

/**
 * Non-empty content (Rne): reporting is done
 * for t only if the content at t is not empty.
 **/
public class NonEmptyContent implements ReportingStrategy {

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return c.size() > 0;
    }

}
