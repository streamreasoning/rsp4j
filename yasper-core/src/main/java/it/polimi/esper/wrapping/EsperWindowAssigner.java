package it.polimi.esper.wrapping;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import it.polimi.spe.content.Content;
import it.polimi.spe.content.viewer.View;
import it.polimi.spe.report.Report;
import it.polimi.spe.report.ReportGrain;
import it.polimi.spe.report.strategies.ReportingStrategy;
import it.polimi.spe.scope.Tick;
import it.polimi.spe.time.Time;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.EncodingUtils;
import it.polimi.yasper.core.utils.EngineConfiguration;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class EsperWindowAssigner implements WindowAssigner, Observer {

    private final EPStatementObjectModel epStatementObjectModel;
    private EPStatement statement;
    private EPRuntime cepRT;
    private EngineConfiguration rsp_config;
    private Time time;
    private Report report;
    private Tick tick;
    private ReportGrain reportGrain = ReportGrain.SINGLE;

    public EsperWindowAssigner(EPStatementObjectModel epStatementObjectModel) {
        this.epStatementObjectModel = epStatementObjectModel;
    }


    @Override
    public void addReportingStrategy(ReportingStrategy strategy) {
        //TODO strategies should be implemented by EPLFactory
        // creating alternative statement templates
    }

    @Override
    public Report getReport() {
        return report;
    }

    @Override
    public Tick getTick() {
        return null;
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
        throw new UnsupportedOperationException("Report must be defined a system level");
    }

    @Override
    public void setTick(Tick timeDriven) {
        throw new UnsupportedOperationException("Tick must be defined a system level");
    }

    @Override
    public void setView(View content) {
        if (content instanceof StatementAwareUpdateListener) {
            statement.addListener((StatementAwareUpdateListener) content);
        }
    }

    @Override
    public void setReportGrain(ReportGrain aw) {
        throw new UnsupportedOperationException("ReportGrain must be defined a system level");
    }

    public boolean process(StreamItem g) {

        if (statement == null) {
            createEPLStatement();
        }

        //Event time vs ingestion time
        long now = rsp_config.isUsingEventTime() ? g.getAppTimestamp() : g.getSysTimestamp();

        if (time.getAppTime() <= now) {
            time.setAppTime(now);
        }

        String encode = EncodingUtils.encode(g.getStreamURI());
        cepRT.sendEvent(g, encode);
        return true;
    }

    private void createEPLStatement() {
        //TODO
        // if on window close output snapshot every betas seconds
        // if periodic  close output snapshot every betas seconds
    }

    @Override
    public void update(Observable o, Object arg) {
        process((StreamItem) arg);
    }
}
