package it.polimi.yasper.core.quering.formatter;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */

@Getter
@RequiredArgsConstructor
public abstract class QueryResponseFormatter implements Observer {

    protected final String format;
    protected final boolean distinct;
    protected ContinuousQueryExecution cqe;

    @Override
    public void update(Observable o, Object arg) {
        this.cqe = this.cqe != null ? (ContinuousQueryExecution) o : this.cqe;
    }
}
