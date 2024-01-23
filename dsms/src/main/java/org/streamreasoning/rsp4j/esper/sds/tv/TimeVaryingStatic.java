package org.streamreasoning.rsp4j.esper.sds.tv;


import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;

/**
 * Created by riccardo on 05/07/2017.
 */
@Log4j
@Getter
public class TimeVaryingStatic<O> implements TimeVarying<O> {

    private final SDS<O> sds;
    protected long now;
    protected Content<O, O> content;
    private String iri;

    public TimeVaryingStatic(SDS<O> sds, O content) {
        this(sds, content, null);
    }


    public TimeVaryingStatic(SDS<O> sds, O content, String iri) {
        this.sds = sds;
        this.iri = iri;
        this.content = new Content<O, O>() {
            @Override
            public int size() {
                return 1;
            }

            @Override
            public void add(O e) {

            }

            @Override
            public Long getTimeStampLastUpdate() {
                return now;
            }

            @Override
            public O coalesce() {
                return content;
            }
        };
    }


    @Override
    public void materialize(long ts) {
        this.content.coalesce();
    }

    @Override
    public O get() {
        return content.coalesce();
    }

    @Override
    public String iri() {
        return iri;
    }

    @Override
    public boolean named() {
        return iri != null;
    }


}
