package it.polimi.jasper.parser.sparql;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.NodeFactory;
/**
 * Created by Riccardo on 06/08/16.
 */
public class RDFLiteral {

    private final String value;
    private String lang;
    private String dt;

    public RDFLiteral(String value) {
        this.value=value;
    }

    public RDFLiteral addLang(String match) {
        this.lang=match;
        return this;
    }

    public RDFLiteral addDT(String s) {
        this.dt=s;
        return this;
    }

    public org.apache.jena.graph.Node build() {
        if ( dt != null ) {
            RDFDatatype dType = TypeMapper.getInstance().getSafeTypeByName(dt) ;
            return NodeFactory.createLiteral(value, dType) ;
        } else if ( lang != null && !lang.isEmpty() )
            return NodeFactory.createLiteral(value, lang) ;
        else
            return NodeFactory.createLiteral(value) ;
    }
}
