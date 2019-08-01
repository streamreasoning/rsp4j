package it.polimi.yasper.core.stream.data;

import it.polimi.yasper.core.operators.s2r.execution.assigner.Consumer;
import it.polimi.yasper.core.stream.web.WebStream;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDFUtils stream?
public interface WebDataStream<I> extends WebStream {

    void addConsumer(Consumer<I> windowAssigner);

    void put(I e, long ts);

}
