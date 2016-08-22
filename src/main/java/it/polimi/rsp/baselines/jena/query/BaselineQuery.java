package it.polimi.rsp.baselines.jena.query;


import it.polimi.heaven.rsp.rsp.querying.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.jena.rdf.model.Model;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaselineQuery implements Query {

    private String id;
    private String[] EPLNamedStreamQueries;
    private String[] EPLStreamQueries;
    private String sparql_query;
    private String[][] esperNamedStreams;
    private String[] esperStreams;

    private Model tbox;

    public boolean hasTBox() {
        return tbox != null;
    }


}
