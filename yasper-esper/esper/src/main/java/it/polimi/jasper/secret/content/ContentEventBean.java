package it.polimi.jasper.secret.content;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.jasper.streams.items.StreamItem;
import it.polimi.yasper.core.secret.content.Content;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;

@Log4j
public abstract class ContentEventBean<I, O> implements Content<I, O> {

    protected List<I> elements;
    @Setter
    private long last_timestamp_changed;

    public ContentEventBean() {
        this.elements = new ArrayList<>();
    }

    public void eval(EventBean[] newData, EventBean[] oldData) {
        DStreamUpdate(oldData);
        IStreamUpdate(newData);
    }

    protected void handleSingleIStream(StreamItem<I> st) {
        // log.debug("Handling single IStreamTest [" + st + "]");
        elements.add(st.getTypedContent());
    }

    private void IStreamUpdate(EventBean[] newData) {
        if (newData != null && newData.length != 0) {
            log.debug("[" + newData.length + "] New Events of type ["
                    + newData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : newData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleIStream((StreamItem<I>) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem<I> st = (StreamItem<I>) meb.get("stream_" + i);
                            handleSingleIStream(st);
                        }
                    }
                }
            }
        }
    }

    protected void DStreamUpdate(EventBean[] oldData) {
        elements.clear();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(I e) {
        elements.add(e);
    }


    public void add(EventBean e) {
        if (e instanceof MapEventBean) {
            MapEventBean meb = (MapEventBean) e;
            if (meb.getUnderlying() instanceof StreamItem) {
                elements.add((I) ((StreamItem<I>) meb.getUnderlying()).getTypedContent());
            } else {
                for (int i = 0; i < meb.getProperties().size(); i++) {
                    StreamItem<I> st = (StreamItem<I>) meb.get("stream_" + i);
                    elements.add(st.getTypedContent());
                }
            }
        }
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return last_timestamp_changed;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    public EventBean[] asArray() {
        return elements.toArray(new EventBean[size()]);
    }

    public void update(EventBean[] newData, EventBean[] oldData, long event_time) {
        eval(newData, oldData);
        last_timestamp_changed = event_time;
    }

    public abstract void replace(O coalesce);
}
