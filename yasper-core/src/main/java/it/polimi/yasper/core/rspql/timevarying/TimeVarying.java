package it.polimi.yasper.core.rspql.timevarying;

public interface TimeVarying {

    void materialize(long ts);

    Object get();

    String iri();

    boolean named();


}
