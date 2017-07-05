package it.polimi.rsp.baselines.rsp;

import it.polimi.rsp.baselines.rsp.stream.element.GraphStimulus;
import lombok.extern.log4j.Log4j;

@Log4j
public class RSPEVA extends RSPQLEngine {
    public RSPEVA() {
        super(new GraphStimulus(), 0);
    }
}
