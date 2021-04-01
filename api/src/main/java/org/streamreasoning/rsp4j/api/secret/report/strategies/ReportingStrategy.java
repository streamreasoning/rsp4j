package org.streamreasoning.rsp4j.api.secret.report.strategies;


import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;

/**
 * According to Botan et al.
 * SPEs use different reporting
 * strategies to define their reporting policy.
 **/
public interface ReportingStrategy {
    boolean match(Window w, Content c, long tapp, long tsys);

}


