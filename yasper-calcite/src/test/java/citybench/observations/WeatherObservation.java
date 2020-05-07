package citybench.observations;

import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import lombok.Getter;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple4;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager.convert;


//hum,tempm,wspdm,TIMESTAMP
@Getter
public class WeatherObservation implements ObservationTuple {
    private final int hum;
    private final double tempm;
    private final double wspdm;
    private final Date TIMESTAMP;

    protected final Set<SchemaEntry> entries = new HashSet<>();
    int i = 0;

    public WeatherObservation(int hum, double tempm, double wspdm, Date timestamp) {
        this.hum = hum;
        this.tempm = tempm;
        this.wspdm = wspdm;
        this.TIMESTAMP = timestamp;
        Field[] fields = this.getClass().getFields();
        Arrays.stream(fields).forEach(field -> {
            String name = field.getType().getSimpleName();
            entries.add(new SimpleSchemaEntry(field.getName(), name, i++, convert(name)));
        });
    }

    @Override
    public StreamSchema<SchemaEntry> schema() {
        return new StreamSchema<SchemaEntry>() {
            @Override
            public Set<SchemaEntry> entrySet() {
                return entries;
            }

            @Override
            public boolean validate(SchemaEntry o) {
                return entries.contains(o);
            }
        };
    }

    @Override
    public Tuple tuple() {
        return new Tuple4<>(hum, tempm, wspdm, TIMESTAMP);
    }
}
