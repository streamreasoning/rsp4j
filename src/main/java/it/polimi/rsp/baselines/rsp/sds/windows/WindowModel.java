package it.polimi.rsp.baselines.rsp.sds.windows;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraphBase;
import it.polimi.rsp.baselines.rsp.stream.RSPListener;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.ModelCom;
import org.apache.jena.rdf.model.impl.ModelReifier;
import org.apache.jena.shared.Command;
import org.apache.jena.shared.Lock;
import org.apache.jena.shared.PrefixMapping;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

@Log4j
@Data
@Getter
@Setter
public abstract class WindowModel extends Observable implements RSPListener {

    protected Model model;
    protected TimeVaryingGraph graph;
    protected Maintenance maintenance;

    public WindowModel(TimeVaryingGraph base) {
        graph = base;
        ModelCom modelCom = new ModelCom(graph);
        model = modelCom;
    }

    public WindowModel() {
        graph = new TimeVaryingGraphBase(-1, this);
        ModelCom modelCom = new ModelCom(graph);
        model = modelCom;
    }


    public long getTimestamp(){
        return getGraph().getTimestamp();
    }
}