package it.polimi.yasper.core.spe.report;


/**
 * Enumeration that lists the possible granularity for the reporting.
 *
 * @MULTIPLE means that at a given time t, one-or-more windows can be simultaneously active
 * and, thus, reported
 * @SINGLE means that at a given time t, one and only one window can be active, and thus reported at
 * at a given time t. (Usually that is the windows with the most recent closing time).
 *
 **/
public enum ReportGrain {
    MULTIPLE, SINGLE
}
