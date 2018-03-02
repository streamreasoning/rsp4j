package it.polimi.spe.content;

import it.polimi.spe.stream.StreamElement;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ContentList implements Content {

    private List<StreamElement> elements;
    @Setter
    private Long last_timestamp_changed;

    public ContentList() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(StreamElement e) {
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


}
