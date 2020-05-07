package it.polimi.csparql2.jena.operators;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import it.polimi.csparql2.jena.stream.JenaGraphContent;
import it.polimi.jasper.operators.s2r.AbstractEsperWindowAssigner;
import it.polimi.jasper.sds.tv.EsperTimeVaryingGeneric;
import it.polimi.jasper.sds.tv.NamedEsperTimeVaryingGeneric;
import it.polimi.jasper.streams.items.StreamItem;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.ReportGrain;
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
import org.apache.jena.graph.Graph;
import org.apache.jena.mem.GraphMem;

import java.util.List;
import java.util.Observable;

@RequiredArgsConstructor
public class EsperGGWindowOperator implements StreamToRelationOperator<Graph, Graph> {

    private final Tick tick;
    private final Report report;
    private final Boolean eventtime;
    private final ReportGrain reportGrain;
    private final Maintenance maintenance;
    private final Time time;
    private final WindowNode wo;
    private final SDS<Graph> context;

    @Override
    public String iri() {
        return wo.iri();
    }

    @Override
    public boolean named() {
        return wo.named();
    }

    @Override
    public TimeVarying<Graph> apply(WebDataStream<Graph> s) {
        EsperGGWindowAssigner consumer = new EsperGGWindowAssigner(s.getURI());
        s.addConsumer(consumer);
        return consumer.set(context);
    }

    class EsperGGWindowAssigner extends AbstractEsperWindowAssigner<Graph, Graph> {

        public EsperGGWindowAssigner(String name) {
            super(name, EsperGGWindowOperator.this.tick, EsperGGWindowOperator.this.maintenance, EsperGGWindowOperator.this.report, EsperGGWindowOperator.this.eventtime, EsperGGWindowOperator.this.time, wo);

        }

        public Content<Graph, Graph> getContent(long now) {
            SafeIterator<EventBean> iterator = statement.safeIterator();
            JenaGraphContent events = new JenaGraphContent(new GraphMem());
            events.setLast_timestamp_changed(now);
            while (iterator.hasNext()) {
                events.add(iterator.next());
            }
            return events;
        }

        @Override
        public List<Content<Graph, Graph>> getContents(long now) {
            return null;
        }


        @Override
        public TimeVarying<Graph> set(SDS<Graph> sds) {
            EsperTimeVaryingGeneric<Graph, Graph> n = named()
                    ? new NamedEsperTimeVaryingGeneric<>(new JenaGraphContent(), wo.iri(), EsperGGWindowOperator.this.maintenance, report, this, sds)
                    : new EsperTimeVaryingGeneric<>(new JenaGraphContent(), EsperGGWindowOperator.this.maintenance, report, this, sds);
            statement.addListener(n);
            return n;
        }

        @Override
        public void notify(Graph arg, long ts) {
            process(arg, ts);
        }

        public boolean process(Graph g, long now) {

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
            StreamItem<Graph> arg1 = (StreamItem<Graph>) arg;
            process(arg1.getTypedContent(), eventtime ? arg1.getAppTimestamp() : arg1.getSysTimestamp());
        }

        @Override
        public Content<Graph, Graph> compute(long l, Window window) {
            return null;
            //TODO
        }

    }
}
