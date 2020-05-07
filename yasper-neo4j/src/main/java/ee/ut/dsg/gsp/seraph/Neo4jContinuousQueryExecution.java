package ee.ut.dsg.gsp.seraph;

import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.stream.data.WebDataStream;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by riccardo on 03/07/2017.
 */
@Log4j
public class Neo4jContinuousQueryExecution extends Observable implements Observer, ContinuousQueryExecution<PGraph, Map<String, Object>, Map<String, Object>> {

    private final RelationToStreamOperator<Map<String, Object>> r2s;
    private final RelationToRelationOperator<Map<String, Object>> r2r;
    private final SDS sds;
    private final ContinuousQuery query;
    private final WebDataStream out;
    private final ContinuousQuery q;
    private List<StreamToRelationOperator<PGraph, PGraph>> s2rs;


    public Neo4jContinuousQueryExecution(WebDataStream out, ContinuousQuery query, SDS sds, RelationToRelationOperator<Map<String, Object>> r2r, RelationToStreamOperator<Map<String, Object>> r2s, StreamToRelationOperator<PGraph, PGraph>... s2rs) {
        this.query = query;
        this.q =  query;
        this.sds = sds;
        this.s2rs = s2rs == null ? new ArrayList<>() : Arrays.asList(s2rs);
        this.r2r = r2r;
        this.r2s = r2s;
        this.out = out;
    }


    @Override
    public void update(Observable o, Object arg) {
        Long now = (Long) arg; // just marks the current time
        sds.materialize(now); // materializes the sds(data) a collection of timevarying variables
        Stream<SolutionMapping<Map<String, Object>>> eval1 = r2r.eval(now);
        /*
        Stream - returns a stream of elements, here it consists of SolutionMappings<PBinding> named eval1
        r2r    - is just a collection of PBindings( Map<String, Object> )
        eval   - does the action
         */

        eval1.forEach(ib -> { // For each Map<String, Object> it does something
            Map<String, Object> eval = r2s.eval(ib, now);
            setChanged(); // Indicates that the objects has now been changed
            if (outstream() != null) {
                outstream().put(eval, now);
            }
            notifyObservers(eval);
        });
    }

    @Override
    public <T> WebDataStream<T> outstream() {
        return out;
    }

    /*
    private PBinding apply2(PBinding eval, Long now) {
        PBinding pgraph = new PGraph(); // creating a propertygraph to populate with Objects (Nodes)
        pgraph.setNodes((List<Node>) eval.values().stream());
        return pgraph;

    }
     */


    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

    @Override
    public SDS<Map<String, Object>> getSDS() {
        return null;
    }

    @Override
    public StreamToRelationOperator<PGraph, Map<String, Object>>[] getS2R() {
        return new StreamToRelationOperator[0];
    }


    public void addS2R(StreamToRelationOperator<PGraph, PGraph> op) {
        s2rs.add(op);
    }


    @Override
    public RelationToRelationOperator<Map<String, Object>> getR2R() {
        return r2r;
    }

    @Override
    public RelationToStreamOperator<Map<String, Object>> getR2S() {
        return r2s;
    }


    @Override
    public void add(QueryResultFormatter o) {
        addObserver(o);
    }

    @Override
    public void remove(QueryResultFormatter o) {
        deleteObserver(o);
    }
}
