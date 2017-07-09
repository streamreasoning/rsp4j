package it.polimi.jasper.parser.sparql;

import lombok.Data;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.BindingFactory;
import org.apache.jena.sparql.engine.binding.BindingMap;
import org.apache.jena.sparql.syntax.ElementData;

/**
 * Created by Riccardo on 11/08/16.
 */
@Data
public class ValuesClauseBuilder {

    private ElementData elm;
    public int currentColumn;

    public ValuesClauseBuilder() {
        elm = new ElementData();
    }

    public ValuesClauseBuilder addVar(Var v) {
        elm.getVars().add(v);
        return this;
    }

    public ValuesClauseBuilder addBinding() {
        elm.getRows().add(BindingFactory.create());
        return this;
    }

    public boolean isValid() {
        return !(currentColumn >= elm.getVars().size());
    }

    public BindingMap currentValueRow() {
        return (BindingMap) elm.getRows().get(elm.getRows().size() - 1);
    }


}
