package it.polimi.sr.onsper.engine;

import it.polimi.rspql.Window;
import it.polimi.rspql.cql.s2_.WindowOperatorNode;
import it.polimi.rspql.instantaneous.Instantaneous;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.esper.EsperStatementView;
import it.polimi.yasper.core.stream.StreamSchema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamedTVR<T> extends EsperStatementView<Relation> {

    private String name;
    private StreamSchema schema;
    private Relation relation;
    private WindowOperatorNode woa;

    public NamedTVR(Maintenance maintenance, WindowOperatorNode wo) {
        super(maintenance);
        this.woa = wo;
    }

    public NamedTVR(String viewName, StreamSchema schema, Relation relation) {
        this.name = viewName;
        this.relation = relation;
        this.schema = schema;
    }


    @Override
    public void update(long t) {
        Window windowContent = woa.getWindowContent(t);
        //FIXME eval(null, null, t);
    }

    @Override
    public Instantaneous eval(long t) {
        update(t);
        return null;
    }

    @Override
    public void setContent(Relation o) {
        relation = o;
    }

    @Override
    public Relation getContent() {
        return relation;
    }

    @Override
    public void setWindowOperator(WindowOperatorNode w) {
        woa = w;
    }
}