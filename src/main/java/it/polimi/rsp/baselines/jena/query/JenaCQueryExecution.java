package it.polimi.rsp.baselines.jena.query;

import it.polimi.heaven.core.teststand.rsp.querying.ContinousQueryExecution;
import it.polimi.rsp.baselines.esper.RSPListener;
import it.polimi.rsp.baselines.jena.JenaListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.jena.query.Dataset;

import java.util.List;

/**
 * Created by Riccardo on 12/08/16.
 */

@Data
@AllArgsConstructor
public class JenaCQueryExecution implements ContinousQueryExecution {

    private Dataset continousResolut;
    private JenaListener executor;

}
