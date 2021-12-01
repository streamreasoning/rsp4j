package org.streamreasoning.rsp4j.esper.sds.tv;


import org.streamreasoning.rsp4j.esper.secret.content.ContentEventBean;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;

@Log4j
@Getter
public class NamedEsperTimeVaryingGeneric<I, O> extends EsperTimeVaryingGeneric<I, O> {

    private String uri;

    public NamedEsperTimeVaryingGeneric(ContentEventBean<I, O> c, String uri, Maintenance maintenance, Report report, StreamToRelationOp<I, O> wa, SDS<O> sds) {
        super(c, maintenance, report, wa, sds);
        this.uri = uri;
    }

    @Override
    public String iri() {
        return uri;
    }

    @Override
    public boolean named() {
        return true;
    }


}