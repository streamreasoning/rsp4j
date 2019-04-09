package it.polimi.yasper.core.format;

import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created by riccardo on 03/07/2017.
 */

@Getter
@RequiredArgsConstructor
public class StreamFormatter implements Assigner {

    protected final String format;
    protected final boolean distinct;
    protected ContinuousQueryExecution cqe;

    @Override
    public Report report() {
        return null;
    }

    @Override
    public Tick tick() {
        return null;
    }

    @Override
    public Time time() {
        return null;
    }

    @Override
    public Content getContent(long now) {
        return null;
    }

    @Override
    public List<Content> getContents(long now) {
        return null;
    }

    @Override
    public TimeVarying set(ContinuousQueryExecution content) {
        return null;
    }

    @Override
    public void notify(Object arg, long ts) {

    }

    @Override
    public String iri() {
        return null;
    }

    @Override
    public boolean named() {
        return false;
    }

    @Override
    public Content compute(long t_e, Window w) {
        return null;
    }
}
