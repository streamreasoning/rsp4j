package it.polimi.rsp.baselines.jena.query;

import it.polimi.heaven.rsp.rsp.querying.ContinousQueryExecution;
import it.polimi.rsp.baselines.jena.sds.SDS;
import it.polimi.rsp.baselines.jena.sds.SDSListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.jena.query.Dataset;

/**
 * Created by Riccardo on 12/08/16.
 */

@Data
@AllArgsConstructor
public class JenaCQueryExecution implements ContinousQueryExecution {

    private Dataset continousResolut;
    private SDS executor;

}
