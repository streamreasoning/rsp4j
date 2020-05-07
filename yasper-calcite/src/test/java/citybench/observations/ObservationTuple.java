package citybench.observations;

import it.polimi.yasper.core.stream.metadata.StreamSchema;
import org.jooq.lambda.tuple.Tuple;

public interface ObservationTuple {

    StreamSchema schema();

    Tuple tuple();
}
