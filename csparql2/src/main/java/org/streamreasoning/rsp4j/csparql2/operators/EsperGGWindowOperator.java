package org.streamreasoning.rsp4j.csparql2.operators;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import org.streamreasoning.rsp4j.esper.operators.s2r.AbstractEsperWindowAssigner;
import org.streamreasoning.rsp4j.esper.sds.tv.EsperTimeVaryingGeneric;
import org.streamreasoning.rsp4j.esper.sds.tv.NamedEsperTimeVaryingGeneric;
import org.streamreasoning.rsp4j.esper.streams.items.StreamItem;
import org.streamreasoning.rsp4j.csparql2.stream.JenaGraphContent;

import lombok.RequiredArgsConstructor;
import org.apache.jena.graph.Graph;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.List;
import java.util.Observable;

@RequiredArgsConstructor
public class EsperGGWindowOperator implements StreamToRelationOp<Graph, Graph> {

    private final Tick tick;
    private final Report report;
    private final Boolean eventtime;
    private final ReportGrain reportGrain;
    private final Maintenance maintenance;
    private final Time time;
    private final WindowNode wo;
    private final SDS<Graph> context;

    @Override
    public Report report() {
        return report;
    }

    @Override
    public Tick tick() {
        return tick;
    }

    @Override
    public Time time() {
        return time;
    }

    @Override
    public ReportGrain grain() {
        return reportGrain;
    }

    @Override
    public Content<Graph, Graph> content(long now) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Content<Graph, Graph>> getContents(long now) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TimeVarying<Graph> get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String iri() {
        return wo.iri();
    }

    @Override
    public boolean named() {
        return wo.named();
    }

    @Override
    public Content<Graph, Graph> compute(long t_e, Window w) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamToRelationOp<Graph, Graph> link(ContinuousQueryExecution<Graph, Graph, ?, ?> context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TimeVarying<Graph> apply(DataStream<Graph> s) {
        EsperGGWindowAssigner consumer = new EsperGGWindowAssigner(s.getName());
        s.addConsumer(consumer);
        return consumer.set(context);
    }

    @Override
    public void notify(Graph arg, long ts) {
        throw new UnsupportedOperationException();
    }

    class EsperGGWindowAssigner extends AbstractEsperWindowAssigner<Graph, Graph> {

        public EsperGGWindowAssigner(String name) {
            super(name, EsperGGWindowOperator.this.tick, EsperGGWindowOperator.this.maintenance, EsperGGWindowOperator.this.report, EsperGGWindowOperator.this.eventtime, EsperGGWindowOperator.this.time, wo);

        }

        public Content<Graph, Graph> getContent(long now) {
            SafeIterator<EventBean> iterator = statement.safeIterator();
            JenaGraphContent events = new JenaGraphContent();
            events.setLast_timestamp_changed(now);
            while (iterator.hasNext()) {
                events.add(iterator.next());
            }
            return events;
        }

        @Override
        public ReportGrain grain() {
            return null;
        }

        @Override
        public Content<Graph, Graph> content(long now) {
            return getContent(now);
        }

        @Override
        public List<Content<Graph, Graph>> getContents(long now) {
            return null;
        }

        @Override
        public TimeVarying<Graph> get() {
            return null;
        }



        @Override
        public StreamToRelationOp<Graph, Graph> link(ContinuousQueryExecution<Graph, Graph, ?, ?> context) {
            return null;
        }

        @Override
        public TimeVarying<Graph> apply(DataStream<Graph> s) {
            throw new UnsupportedOperationException();

        }


        // @Override
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
