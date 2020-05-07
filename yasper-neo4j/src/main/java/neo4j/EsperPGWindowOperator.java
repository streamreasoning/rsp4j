package neo4j;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import it.polimi.jasper.operators.s2r.AbstractEsperWindowAssigner;
import it.polimi.jasper.sds.tv.EsperTimeVaryingGeneric;
import it.polimi.jasper.sds.tv.NamedEsperTimeVaryingGeneric;
import it.polimi.jasper.streams.items.StreamItem;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.data.WebDataStream;
import lombok.RequiredArgsConstructor;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.List;
import java.util.Observable;

@RequiredArgsConstructor
public class EsperPGWindowOperator implements StreamToRelationOperator<PGraph, PGraph> {

    private final Tick tick;
    private final Report report;
    private final Boolean eventtime;
    private final Maintenance maintenance;
    private final Time time;
    private final WindowNode wo;
    private final SDS<PGraph> context;
    private final GraphDatabaseService db;

    @Override
    public String iri() {
        return wo.iri();
    }

    @Override
    public boolean named() {
        return wo.named();
    }

    @Override
    public TimeVarying<PGraph> apply(WebDataStream<PGraph> s) {
        EsperGGWindowAssigner consumer = new EsperGGWindowAssigner(s.getURI());
        s.addConsumer(consumer);
        return consumer.set(context);
    }

    class EsperGGWindowAssigner extends AbstractEsperWindowAssigner<PGraph, PGraph> {

        public EsperGGWindowAssigner(String name) {
            super(name, EsperPGWindowOperator.this.tick, Maintenance.NAIVE, EsperPGWindowOperator.this.report, EsperPGWindowOperator.this.eventtime, EsperPGWindowOperator.this.time, EsperPGWindowOperator.this.wo);
        }

        public Content<PGraph, PGraph> getContent(long now) {
            SafeIterator<EventBean> iterator = statement.safeIterator();
            ContentPGraphBean events = new ContentPGraphBean(db);
            events.setLast_timestamp_changed(now);
            while (iterator.hasNext()) {
                events.add(iterator.next());
            }
            return events;
        }

        @Override
        public List<Content<PGraph, PGraph>> getContents(long now) {
            return null;
        }


        @Override
        public TimeVarying<PGraph> set(SDS<PGraph> sds) {
            EsperTimeVaryingGeneric<PGraph, PGraph> n = named()
                    ? new NamedEsperTimeVaryingGeneric<>(new ContentPGraphBean(db), name, EsperPGWindowOperator.this.maintenance, report, this, sds)
                    : new EsperTimeVaryingGeneric<>(new ContentPGraphBean(db), EsperPGWindowOperator.this.maintenance, report, this, sds);
            statement.addListener(n);
            return n;
        }

        @Override
        public void notify(PGraph arg, long ts) {
            process(arg, ts);
        }

        public boolean process(PGraph g, long now) {

            long appTime = time.getAppTime();

            if (appTime < now) {
                time.setAppTime(now);
                runtime.sendEvent(new StreamItem<>(now, g, name), name);
                return true;
            } else if (appTime == now) {
                runtime.sendEvent(new StreamItem<>(now, g, name), name);
                return true;
            } else
                return false;

        }

        @Override
        public void update(Observable o, Object arg) {
            StreamItem<PGraph> arg1 = (StreamItem<PGraph>) arg;
            process(arg1.getTypedContent(), eventtime ? arg1.getAppTimestamp() : arg1.getSysTimestamp());
        }

        @Override
        public Content<PGraph, PGraph> compute(long l, Window window) {
            return null;
            //TODO
        }

    }
}
