package it.polimi.yasper.core.quering.rspql.tvg;

public interface TimeVarying {

    void materialize(long ts);

    Object get();
}
