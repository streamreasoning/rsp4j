package it.polimi.yasper.simple.windowing;

public interface TimeVarying<T> {

    T eval(long ts);

    T asT();

}
