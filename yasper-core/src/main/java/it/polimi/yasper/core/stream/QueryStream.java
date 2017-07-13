package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.CreateSchemaClause;
import com.espertech.esper.client.soda.SchemaColumnDesc;
import it.polimi.yasper.core.engine.RSPEngine;
import it.polimi.yasper.core.query.ContinuousQuery;
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
public class QueryStream extends StreamImpl {

    protected RSPEngine e;
    @NonNull
    protected String query_id;
    @Setter
    @Getter
    protected EPStatement streamStatemnt;
    protected ContinuousQuery q;

    public QueryStream(RSPEngine e, String stream_uri, EPStatement streamStatemnt) {
        super(stream_uri, streamStatemnt);
        this.e = e;
    }

    public QueryStream(RSPEngine e, String stream_uri) {
        super(stream_uri);
        this.query_id = stream_uri;
        this.e = e;
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
        schema.setSchemaName(EncodingUtils.encode(query_id));
        schema.setInherits(new HashSet<String>(Arrays.asList(new String[]{"TStream"})));
        List<SchemaColumnDesc> columns = new ArrayList<SchemaColumnDesc>();
        schema.setColumns(columns);
        StringWriter writer = new StringWriter();
        schema.toEPL(writer);
        return writer.toString();
    }
}
