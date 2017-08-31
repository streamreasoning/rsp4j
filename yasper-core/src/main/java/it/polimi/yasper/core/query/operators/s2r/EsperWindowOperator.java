package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riccardo on 12/07/2017.
 */
@Getter
@Setter

@Log4j
public class EsperWindowOperator extends WindowOperatorImpl {

    private EPStatement eps;

    public EsperWindowOperator(EPStatement eps, long t0, long range, long step) {
        super(t0, range,step);
        this.eps = eps;
    }

    @Override
    public void addListener(TimeVaryingGraph tvg) {
        eps.addListener(tvg);
    }

    @Override
    public EventBean[] getWindowContent(long t0) {
        List<EventBean> events = new ArrayList<EventBean>();

        log.debug("[" + System.currentTimeMillis() + "] Polling STATEMENT: " + eps.getText() + " "
                + eps.getTimeLastStateChange() + "AT [" + (Long) t0 + "]");

        SafeIterator<EventBean> it = eps.safeIterator();

        while (it.hasNext()) {
            EventBean next = it.next();
            log.debug(next.getUnderlying());
            events.add(next);
        }
        it.close();
        return events.toArray(new EventBean[events.size()]);
    }

    @Override
    public String getName() {
        return eps.getName();
    }

    @Override
    public String getText() {
        return eps.getText();
    }

    private void handleSingleIStream(InstantaneousItem ii, StreamItem st) {
        log.debug("Handling single IStreamTest [" + st + "]");
        st.addTo(ii);
    }

    public void IStreamUpdate(InstantaneousItem ii, EventBean[] newData) {
        if (newData != null && newData.length != 0) {
            log.debug("[" + newData.length + "] New Events of type ["
                    + newData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : newData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleIStream(ii, (StreamItem) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem st = (StreamItem) meb.get("stream_" + i);
                            handleSingleIStream(ii, st);
                        }
                    }
                }
            }
        }
    }

    private void handleSingleDStream(InstantaneousItem ii, StreamItem st) {
        log.debug("Handling single IStreamTest [" + st + "]");
        st.removeFrom(ii);
    }

    public void DStreamUpdate(InstantaneousItem ii, EventBean[] oldData, Maintenance m) {
        if (Maintenance.NAIVE.equals(m)) {
            ii.clear();
        } else {
            if (oldData != null) {
                log.debug("[" + oldData.length + "] Old Events of type ["
                        + oldData[0].getUnderlying().getClass().getSimpleName() + "]");
                for (EventBean e : oldData) {
                    if (e instanceof MapEventBean) {
                        MapEventBean meb = (MapEventBean) e;
                        if (meb.getProperties() instanceof StreamItem) {
                            handleSingleDStream(ii, (StreamItem) e.getUnderlying());
                        } else {
                            for (int i = 0; i < meb.getProperties().size(); i++) {
                                StreamItem st = (StreamItem) meb.get("stream_" + i);
                                handleSingleDStream(ii, st);
                            }
                        }
                    }
                }
            }
        } //Remove all the data

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null) return false;
        else if (o instanceof EPStatement)
            return eps.equals(o);
        else if (o instanceof EsperWindowOperator) {
            EsperWindowOperator that = (EsperWindowOperator) o;
            return eps != null ? eps.equals(that.eps) : that.eps == null;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return eps != null ? eps.hashCode() : 0;
    }
}
