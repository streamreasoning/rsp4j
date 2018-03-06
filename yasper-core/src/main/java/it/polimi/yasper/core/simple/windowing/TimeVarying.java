package it.polimi.yasper.core.simple.windowing;

public interface TimeVarying<T> {

    T eval(long ts);

    T asT();

}
