package it.polimi.yasper.core.secret.report.strategies;


import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;

/**
 * According to Botan et al.
 * SPEs use different reporting
 * strategies to define their reporting policy.
 **/
public interface ReportingStrategy {
    boolean match(Window w, Content c, long tapp, long tsys);

}


