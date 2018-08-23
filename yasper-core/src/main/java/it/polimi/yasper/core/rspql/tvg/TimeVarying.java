package it.polimi.yasper.core.rspql.tvg;

public interface TimeVarying {

    void materialize(long ts);

    Object get();

    String iri();

    boolean named();


}
