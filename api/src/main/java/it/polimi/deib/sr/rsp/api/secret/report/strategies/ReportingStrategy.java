package it.polimi.deib.sr.rsp.api.secret.report.strategies;


import it.polimi.deib.sr.rsp.api.secret.content.Content;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.instance.Window;

/**
 * According to Botan et al.
 * SPEs use different reporting
 * strategies to define their reporting policy.
 **/
public interface ReportingStrategy {
    boolean match(Window w, Content c, long tapp, long tsys);

}


