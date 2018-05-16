package it.polimi.yasper.core.quering;

public interface TimeVarying<T> {

    T eval(long ts);

    T asT();

}
