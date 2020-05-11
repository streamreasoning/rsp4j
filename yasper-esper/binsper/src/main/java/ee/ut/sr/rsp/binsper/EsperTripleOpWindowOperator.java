package ee.ut.sr.rsp.binsper;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
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
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.algebra.op.OpTriple;
import org.apache.jena.sparql.engine.binding.Binding;

import java.util.List;
import java.util.Observable;
import java.util.Set;

@RequiredArgsConstructor
public class EsperTripleOpWindowOperator implements StreamToRelationOperator<Triple, Set<Binding>> {

    private final Tick tick;
    private final Report report;
    private final Boolean eventtime;
    private final ReportGrain reportGrain;
    private final Maintenance maintenance;
    private final Time time;
    private final WindowNode wo;
    private final SDS<Set<Binding>> sds;

    private final String name;
    private final List<OpTriple> ops;

    @Override
    public String iri() {
        return wo.iri();
    }

    @Override
    public boolean named() {
        return wo.named();
    }

    @Override
    public TimeVarying<Set<Binding>> apply(WebDataStream<Triple> s) {
        EsperTBWindowAssigner consumer = new EsperTBWindowAssigner(s.getURI());
        s.addConsumer(consumer);
        return consumer.set(sds);
    }

    class EsperTBWindowAssigner extends AbstractEsperWindowAssigner<Triple, Set<Binding>> {

        public EsperTBWindowAssigner(String name) {
            super(name, EsperTripleOpWindowOperator.this.tick, EsperTripleOpWindowOperator.this.maintenance, EsperTripleOpWindowOperator.this.report, EsperTripleOpWindowOperator.this.eventtime, EsperTripleOpWindowOperator.this.time, EsperTripleOpWindowOperator.this.wo);
        }

        public Content<Triple, Set<Binding>> getContent(long now) {
            SafeIterator<EventBean> iterator = statement.safeIterator();
            JenaBindingContent content2 = new JenaBindingContent(EsperTripleOpWindowOperator.this.ops);
            content2.setLast_timestamp_changed(now);
            while (iterator.hasNext()) {
                content2.add(iterator.next());
            }
            return content2;
        }

        @Override
        public List<Content<Triple, Set<Binding>>> getContents(long now) {
            return null;
        }

        @Override
        public TimeVarying<Set<Binding>> set(SDS<Set<Binding>> sds) {
            EsperTimeVaryingGeneric<Triple, Set<Binding>> n = named()
                    ? new NamedEsperTimeVaryingGeneric<>(new JenaBindingContent(EsperTripleOpWindowOperator.this.ops), name, EsperTripleOpWindowOperator.this.maintenance, report, this, sds)
                    : new EsperTimeVaryingGeneric<>(new JenaBindingContent(EsperTripleOpWindowOperator.this.ops), EsperTripleOpWindowOperator.this.maintenance, report, this, sds);
            statement.addListener(n);
            return n;
        }

        @Override
        public void notify(Triple arg, long ts) {
            process(arg, ts);
        }

        public boolean process(Triple g, long now) {

            long appTime = time.getAppTime();

            if (appTime < now) {
                time.setAppTime(now);
                runtime.sendEvent(new StreamItem<Triple>(now, g, name), name);
                return true;
            } else if (appTime == now) {
                runtime.sendEvent(new StreamItem<Triple>(now, g, name), name);
                return true;
            } else
                return false;

        }

        @Override
        public void update(Observable o, Object arg) {
            StreamItem<Triple> arg1 = (StreamItem<Triple>) arg;
            process(arg1.getTypedContent(), eventtime ? arg1.getAppTimestamp() : arg1.getSysTimestamp());
        }

        @Override
        public Content<Triple, Set<Binding>> compute(long l, Window window) {
            return null;
            //TODO
        }
    }
}
