package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.CreateSchemaClause;
import com.espertech.esper.client.soda.SchemaColumnDesc;
import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.utils.EncodingUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by riccardo on 10/07/2017.
 */
public class StreamImpl implements Stream {

    protected RSPEngine e;
    @NonNull
    protected String stream_uri;
    @Setter
    @Getter
    protected EPStatement streamStatemnt;

    public StreamImpl(String stream_uri, EPStatement streamStatemnt) {
        this.e = e;
        this.stream_uri = stream_uri;
        this.streamStatemnt = streamStatemnt;
    }

    public StreamImpl(String stream_uri) {
        this.e = e;
        this.stream_uri = stream_uri;
        this.streamStatemnt = streamStatemnt;
    }

    @Override
    public void setRSPEngine(RSPEngine e) {
        this.e = e;
    }

    @Override
    public RSPEngine getRSPEngine() {
        return e;
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
