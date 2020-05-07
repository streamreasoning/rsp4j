package citybench.observations;

import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import lombok.Getter;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple6;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager.convert;

//vehiclecount,updatetime,_id,totalspaces,garagecode,streamtime
@Getter
public class AarhusParkingObservation implements ObservationTuple {

    private final Integer vehiclecount;
    private final Date updatetime;
    private final Integer _id;
    private final Integer totalspaces;
    private final String garagecode;
    private final Date streamtime;

    private int i = 0;
    private final Set<SchemaEntry> entries = new HashSet<>();

    public AarhusParkingObservation(Integer vehiclecount, Date updatetime, Integer _id, Integer totalspaces, String garagecode, Date streamtime) {
        Field[] fields = this.getClass().getFields();
        Arrays.stream(fields).forEach(field -> {
            String name = field.getType().getSimpleName();
            entries.add(new SimpleSchemaEntry(field.getName(), name, i++, convert(name)));
        });

        this.vehiclecount = vehiclecount;
        this.updatetime = updatetime;
        this._id = _id;
        this.totalspaces = totalspaces;
        this.garagecode = garagecode;
        this.streamtime = streamtime;
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
        return new Tuple6<>(vehiclecount, updatetime, _id, totalspaces, garagecode, streamtime);
    }
}
