package it.polimi.rsp.baselines.jena.sds.observer;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.rsp.baselines.enums.Reasoning;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;

import java.util.Set;

@Log4j
@Getter
public class DefaultTVG extends TimeVaryingGraph {

    private Set<EPStatement> statements;
    private EPStatement latest;

    public DefaultTVG(Reasoning reasoning, Set<EPStatement> stmts) {
        super(reasoning);
        this.statements = stmts;
    }

    public DefaultTVG(Reasoning reasoning, Graph g, Set<EPStatement> stmts) {
        super(reasoning, g);
        this.statements = stmts;
    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        log.info("[" + Thread.currentThread() + "][" + System.currentTimeMillis() + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + esp.getEPRuntime().getCurrentTime());

        super.update(newData, oldData, stmt, esp);

        this.latest = stmt;
        notifyObservers(esp);

    }

    @Override
    public EPStatement getTriggeringStatement() {
        return latest;
    }


}