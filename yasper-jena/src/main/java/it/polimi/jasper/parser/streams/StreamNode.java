package it.polimi.jasper.parser.streams;

import com.espertech.esper.client.soda.CreateSchemaClause;
import com.espertech.esper.client.soda.SchemaColumnDesc;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.utils.EncodingUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.jena.graph.Node_URI;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Riccardo on 14/08/16.
 */
@AllArgsConstructor
public class StreamNode implements Stream {
    @Getter
    @Setter

    private Node_URI iri;

    @Override


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreamNode stream = (StreamNode) o;

        return iri != null ? iri.equals(stream.iri) : stream.iri == null;
    }

    @Override
    public int hashCode() {
        return iri != null ? iri.hashCode() : 0;
    }

    @Override
    public String getURI() {
        return iri.getURI();
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {

    }

    public String toEPLSchema() {
        CreateSchemaClause schema = new CreateSchemaClause();
        schema.setSchemaName(EncodingUtils.encode(iri.getURI()));
        schema.setInherits(new HashSet<String>(Arrays.asList(new String[]{"TStream"})));
        List<SchemaColumnDesc> columns = new ArrayList<SchemaColumnDesc>();
        schema.setColumns(columns);
        StringWriter writer = new StringWriter();
        schema.toEPL(writer);
        return writer.toString();
    }

    @Override
    public String toString() {
        return "StreamNode{" + "iri=" + iri + '}';
    }

}
