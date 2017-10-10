package it.polimi.sr.onsper.query;

import it.polimi.rspql.querying.ContinuousQuery;
import it.unibz.inf.ontop.answering.reformulation.input.InputQuery;
import org.semanticweb.owlapi.model.IRI;

public interface OBDAQuery extends ContinuousQuery {

    InputQuery getQ();

    IRI getTBox();
}
