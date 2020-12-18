package it.polimi.deib.sr.rsp.api.stream.data;

import it.polimi.deib.sr.rsp.api.stream.web.WebStream;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.Consumer;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDFUtils stream?
public interface WebDataStream<I> extends WebStream {

    void addConsumer(Consumer<I> windowAssigner);

    void put(I e, long ts);

}
