package it.polimi.jasper.engine.instantaneous;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.impl.ModelCom;

import java.util.function.Supplier;

@Log4j
@Data
@Getter
@Setter
public class InstantaneousModelCom extends InstantaneousModel {

    public InstantaneousModelCom(InstantaneousGraph base) {
        super(base);
        this.model = new ModelCom(base);
    }

    public long getTimestamp() {
        return getGraph().getTimestamp();
    }

    @Override
    public <T> T calculateInTxn(Supplier<T> action) {
        return ((ModelCom) model).calculateInTxn(action);
    }
}