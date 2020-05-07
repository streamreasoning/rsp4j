package citybench.observations;


import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple8;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.OBDAManager.convert;

//ozone,particullate_matter,carbon_monoxide,sulfure_dioxide,nitrogen_dioxide,longitude,latitude,timestamp
public class PollutionObservation implements ObservationTuple {


    private final Integer ozone;
    private final Integer particullate_matter;
    private final Integer carbon_monoxide;
    private final Integer sulfure_dioxide;
    private final Integer nitrogen_dioxide;
    private final Double longitude;
    private final Double latitude;
    private final Date timestamp;
    private final Set<SchemaEntry> entries = new HashSet<>();
    private int i = 0;

    public PollutionObservation(Integer ozone, Integer particullate_matter, Integer carbon_monoxide, Integer sulfure_dioxide, Integer nitrogen_dioxide, Double longitude, Double latitude, Date timestamp) {
        this.ozone = ozone;
        this.particullate_matter = particullate_matter;
        this.carbon_monoxide = carbon_monoxide;
        this.sulfure_dioxide = sulfure_dioxide;
        this.nitrogen_dioxide = nitrogen_dioxide;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;

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
        return new Tuple8<>(ozone, particullate_matter, carbon_monoxide, sulfure_dioxide, nitrogen_dioxide, longitude, latitude, timestamp);

    }
}
