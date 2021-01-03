package it.polimi.deib.sr.rsp.api.sds.timevarying;

public interface TimeVarying<E> {

    void materialize(long ts);

    E get();

    String iri();

    default boolean named() {
        return iri() != null;
    }

}
