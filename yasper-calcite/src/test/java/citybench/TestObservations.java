package citybench;

import citybench.observations.AarhusParkingObservation;
import citybench.observations.AarhusTrafficObservation;
import citybench.observations.PollutionObservation;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;
import org.junit.Test;

import java.sql.Date;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestObservations {

    @Test
    public void testParkingObs() throws ParseException {

        //vehiclecount,updatetime,_id,totalspaces,garagecode,streamtime
        //
        String[] csv_line = "0,2014-05-22 09:09:04.145,1,65,NORREPORT,2014-11-03 16:18:44".split(",");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        AarhusParkingObservation obs = new AarhusParkingObservation(
                Integer.parseInt(csv_line[0]),
                formatter.parse(csv_line[1]),
                Integer.parseInt(csv_line[2]),
                Integer.parseInt(csv_line[3]),
                csv_line[4],
                formatter2.parse(csv_line[5])
        );

        Tuple tuple = obs.tuple();

        System.out.println(tuple.toString());

        assertTrue(tuple instanceof Tuple6);

        assertEquals(new Tuple6<>(Integer.parseInt(csv_line[0]),
                formatter.parse(csv_line[1]),
                Integer.parseInt(csv_line[2]),
                Integer.parseInt(csv_line[3]),
                csv_line[4],
                formatter2.parse(csv_line[5])), tuple);

        Set<SchemaEntry> entries = new HashSet<>();

        entries.add(new SimpleSchemaEntry("vehiclecount", "Integer", 1, Types.INTEGER));
        entries.add(new SimpleSchemaEntry("updatetime", "Date", 2, Types.DATE));
        entries.add(new SimpleSchemaEntry("_id", "Integer", 3, Types.INTEGER));
        entries.add(new SimpleSchemaEntry("totalspaces", "Integer", 4, Types.INTEGER));
        entries.add(new SimpleSchemaEntry("garagecode", "String", 5, Types.VARCHAR));
        entries.add(new SimpleSchemaEntry("streamtime", "Date", 6, Types.DATE));

    }


    @Test
    public void testTrafficObs() throws ParseException {

        //status,avgMeasuredTime,avgSpeed,extID,medianMeasuredTime,TIMESTAMP,vehicleCount,_id,REPORT_ID
        String[] csv_line = "OK,74,50,668,74,2014-08-01T07:50:00,5,20746220,158324".split(",");

        System.out.println(csv_line[5]);
        TemporalAccessor parse = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(csv_line[5]);
        LocalDateTime localDateTime = LocalDateTime.from(parse);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Instant result = Instant.from(zonedDateTime);

        AarhusTrafficObservation obs = new AarhusTrafficObservation(
                csv_line[0],
                Double.parseDouble(csv_line[1]),
                Double.parseDouble(csv_line[2]),
                Integer.parseInt(csv_line[3]),
                csv_line[4],
                Date.from(result),
                Integer.parseInt(csv_line[6]),
                csv_line[7],
                csv_line[8]
        );

        Tuple tuple = obs.tuple();

        System.out.println(tuple.toString());

        assertTrue(tuple instanceof Tuple9);

    }

    @Test
    public void testPollutionObs() throws ParseException {

        //ozone,particullate_matter,carbon_monoxide,sulfure_dioxide,nitrogen_dioxide,longitude,latitude,timestamp
        String[] csv_line = "101,94,49,44,87,10.104986076057457,56.23172069428216,2014-08-01 00:05:00".split(",");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        PollutionObservation obs = new PollutionObservation(
                Integer.parseInt(csv_line[0]),
                Integer.parseInt(csv_line[1]),
                Integer.parseInt(csv_line[2]),
                Integer.parseInt(csv_line[3]),
                Integer.parseInt(csv_line[4]),
                Double.parseDouble(csv_line[5]),
                Double.parseDouble(csv_line[6]),
                formatter.parse(csv_line[7])
        );

        Tuple tuple = obs.tuple();

        System.out.println(tuple.toString());

        assertTrue(tuple instanceof Tuple8);

    }
}