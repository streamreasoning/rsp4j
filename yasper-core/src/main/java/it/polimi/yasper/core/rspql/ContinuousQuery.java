package it.polimi.yasper.core.rspql;

import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.enums.StreamOperator;

import java.util.Map;

public interface ContinuousQuery {

    String getID();

    StreamOperator getR2S();

    boolean isRecursive();

    Map<? extends WindowOperator, Stream> getWindowMap();

    void accept(SDSBuilder v);

}
