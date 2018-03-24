package it.polimi.rsp.yasper.csparql;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import it.polimi.jasper.engine.spe.content.TimeVaryingJenaGraph;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.simple.windowing.TimeVarying;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.jena.graph.Graph;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class EsperWindowAssigner implements WindowAssigner<Graph>, Observer {

    private EPAdministrator admin;
    private EPStatement statement;
    private EPRuntime runtime;
    private EngineConfiguration rsp_config;
    private Time time;
    private Report report;
    private Tick tick = Tick.TIME_DRIVEN;
    private ReportGrain reportGrain = ReportGrain.SINGLE;

    public EsperWindowAssigner(String name, EPStatementObjectModel epStatementObjectModel) throws ConfigurationException {
        this.rsp_config = EngineConfiguration.getCurrent();
        this.report = rsp_config.getReport();
        this.time = TimeFactory.getInstance();
        this.statement = admin.create(epStatementObjectModel, name);
    }

    @Override
    public Report getReport() {
        return report;
    }

    @Override
    public Tick getTick() {
        return tick;
    }

    @Override
    public Content getContent(long now) {
        return null;
    }

    @Override
    public List<Content> getContents(long now) {
        return null;
    }

    @Override
    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public void setTick(Tick tick) {
        this.tick = tick;
    }

    @Override
    public TimeVarying<Graph> setView(View content) {
        content.observerOf(statement);
        return new TimeVaryingJenaGraph();
    }

    @Override
    public void setReportGrain(ReportGrain aw) {
        this.reportGrain = aw;
    }

    @Override
    public void notify(StreamElement arg) {
        process((RdfQuadruple) arg.getContent())
    }

    public boolean process(RdfQuadruple g) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
