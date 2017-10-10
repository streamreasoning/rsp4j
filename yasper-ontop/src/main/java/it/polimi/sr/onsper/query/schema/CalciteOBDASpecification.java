package it.polimi.sr.onsper.query.schema;

import it.unibz.inf.ontop.dbschema.DBMetadata;
import it.unibz.inf.ontop.spec.OBDASpecification;
import it.unibz.inf.ontop.spec.mapping.Mapping;
import it.unibz.inf.ontop.spec.ontology.ImmutableOntologyVocabulary;
import it.unibz.inf.ontop.spec.ontology.TBoxReasoner;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalciteOBDASpecification implements OBDASpecification {

    private final Mapping mappings;
    private final TBoxReasoner tbox;
    private final ImmutableOntologyVocabulary vocabulary;
    private final DBMetadata metadata;

    @Override
    public Mapping getSaturatedMapping() {
        return null;
    }

    @Override
    public DBMetadata getDBMetadata() {
        return null;
    }

    @Override
    public TBoxReasoner getSaturatedTBox() {
        return null;
    }

    @Override
    public ImmutableOntologyVocabulary getVocabulary() {
        return null;
    }
}
