package it.polimi.yasper.core.stream;

import com.espertech.esper.client.soda.CreateSchemaClause;
import com.espertech.esper.client.soda.SchemaColumnDesc;
import it.polimi.yasper.core.utils.EncodingUtils;
import lombok.NonNull;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by riccardo on 10/07/2017.
 */
public class StreamImpl implements Stream {

    @NonNull
    protected String stream_uri;

    public StreamImpl(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public String getURI() {
        return stream_uri;
    }


    public String toEPLSchema() {
        CreateSchemaClause schema = new CreateSchemaClause();
        schema.setSchemaName(EncodingUtils.encode(stream_uri));
        schema.setInherits(new HashSet<String>(Arrays.asList(new String[]{"TStream"})));
        List<SchemaColumnDesc> columns = new ArrayList<SchemaColumnDesc>();
        schema.setColumns(columns);
        StringWriter writer = new StringWriter();
        schema.toEPL(writer);
        return writer.toString();
    }
}
