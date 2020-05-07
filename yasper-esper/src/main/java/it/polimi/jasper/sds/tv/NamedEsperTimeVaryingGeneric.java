package it.polimi.jasper.sds.tv;


import it.polimi.jasper.secret.content.ContentEventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.secret.report.Report;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class NamedEsperTimeVaryingGeneric<I, O> extends EsperTimeVaryingGeneric<I, O> {

    private String uri;

    public NamedEsperTimeVaryingGeneric(ContentEventBean<I, O> c, String uri, Maintenance maintenance, Report report, Assigner<I, O> wa, SDS<O> sds) {
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