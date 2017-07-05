package it.polimi.rsp.baselines.rsp.query.response;

import it.polimi.services.FileService;
import it.polimi.sr.rsp.RSPQuery;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;


@Getter
@Log4j
public final class ConstructResponse extends InstantaneousResponse {
    private Model results;

    public ConstructResponse(String id, RSPQuery query, Model results, long cep_timestamp) {
        super(id, System.currentTimeMillis(), cep_timestamp, query);
        this.results = results;
    }

    @Override
    public boolean save(String where) {
        log.debug("Save Data [" + where + "]");

        return FileService.write(where + ".trig", getData());
    }

    private String getData() {

        String eol = System.getProperty("line.separator");
        String trig = getId() + " {";
        StmtIterator listStatements = results.listStatements();
        while (listStatements.hasNext()) {
            Statement s = listStatements.next();
            trig += eol + "<" + s.getSubject().toString() + ">" + " " + "<" + s.getPredicate().toString() + ">" + " " + "<"
                    + s.getObject().toString() + "> .";
        }
        trig += eol + "}" + eol;
        return trig;
    }
}
