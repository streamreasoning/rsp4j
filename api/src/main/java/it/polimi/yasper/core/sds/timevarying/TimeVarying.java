package it.polimi.yasper.core.sds.timevarying;

public interface TimeVarying<E> {

    void materialize(long ts);

    E get();

    String iri();

    boolean named();

}
