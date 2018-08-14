package it.polimi.yasper.core.spe.report.strategies;


import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.windowing.definition.Window;

/**
 * According to Botan et al.
 * SPEs use different reporting
 * strategies to define their reporting policy.
 **/
public interface ReportingStrategy {
    boolean match(Window w, Content c, long tapp, long tsys);

}


