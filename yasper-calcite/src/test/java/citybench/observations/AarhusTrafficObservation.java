package citybench.observations;

import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import lombok.Getter;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple9;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager.convert;


@Getter
//status,avgMeasuredTime,avgSpeed,extID,medianMeasuredTime,TIMESTAMP,vehicleCount,_id,REPORT_ID
public class AarhusTrafficObservation implements ObservationTuple {

    private final String status;
    private final Double avgMeasuredTime;
    private final Double avgSpeed;
    private final Integer extID;
    private final String medianMeasuredTime;
    private final Date timestamp;
    private final Integer vehicleCount;
    private final String id;
    private final String report_id;

    private final Set<SchemaEntry> entries = new HashSet<>();
    private int i = 0;

    public AarhusTrafficObservation(String status, Double avgMeasuredTime, Double avgSpeed, Integer extID, String medianMeasuredTime, Date timestamp, Integer vehicleCount, String id, String report_id) {
        this.status = status;
        this.avgMeasuredTime = avgMeasuredTime;
        this.avgSpeed = avgSpeed;
        this.extID = extID;
        this.medianMeasuredTime = medianMeasuredTime;
        this.timestamp = timestamp;
        this.vehicleCount = vehicleCount;
        this.id = id;
        this.report_id = report_id;

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
            public boolean validate(SchemaEntry schemaEntry) {
                return entries.contains(schemaEntry);
            }
        };

    }

    @Override
    public Tuple tuple() {
        return new Tuple9<>(status, avgMeasuredTime, avgSpeed, extID, medianMeasuredTime, timestamp, vehicleCount, id, report_id);
    }
}
