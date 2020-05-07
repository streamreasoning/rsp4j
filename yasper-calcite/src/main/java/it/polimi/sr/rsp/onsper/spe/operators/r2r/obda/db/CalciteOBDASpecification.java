package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db;

import it.unibz.inf.ontop.dbschema.DBMetadata;
import it.unibz.inf.ontop.spec.OBDASpecification;
import it.unibz.inf.ontop.spec.mapping.Mapping;
import it.unibz.inf.ontop.spec.ontology.ClassifiedTBox;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalciteOBDASpecification implements OBDASpecification {

    private final Mapping mappings;
    private final ClassifiedTBox vocabulary;
    private final DBMetadata metadata;

    @Override
    public Mapping getSaturatedMapping() {
        return mappings;
    }

    @Override
    public DBMetadata getDBMetadata() {
        return metadata;
    }

    @Override
    public ClassifiedTBox getSaturatedTBox() {
        return vocabulary;
    }

}
