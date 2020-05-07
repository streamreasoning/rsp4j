package it.polimi.sr.rsp.onsper.spe.content;

import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.yasper.core.secret.content.Content;
import lombok.Setter;
import org.jooq.lambda.tuple.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ContentRel implements Content<Tuple, Relation<Tuple>> {

    private List<Tuple> elements;

    @Setter
    private long last_timestamp_changed;

    public ContentRel() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(Tuple e) {
        //In the content the temporal dimension is lost
        elements.add(e);
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return last_timestamp_changed;
    }

    @Override
    public Relation<Tuple> coalesce() {

        return new Relation<Tuple>() {
            @Override
            public Collection<Tuple> getCollection() {
                return elements;
            }

            @Override
            public void add(Tuple o) {
                elements.add(o);
            }

            @Override
            public void remove(Tuple o) {
                elements.remove(o);
            }

            @Override
            public void clear() {
                elements.clear();
            }
        };
    }


    @Override
    public String toString() {
        return elements.toString();
    }


    @Override
    public int hashCode() {
        return Objects.hash(elements, last_timestamp_changed);
    }

}
