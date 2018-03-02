package it.polimi.yasper.core.spe.content;

import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.spe.stream.StreamElement;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ContentBean implements Content {

    private List<EventBean> elements;
    @Setter
    private long last_timestamp_changed;

    public ContentBean() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(StreamElement e) {
        add((EventBean) e.getContent());
        System.out.println();
        this.last_timestamp_changed = e.getTimestamp();
    }

    public void add(EventBean e) {
        elements.add(e);
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
}
