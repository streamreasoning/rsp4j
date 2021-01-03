package it.polimi.deib.sr.rsp.api.engine.config;

import it.polimi.deib.sr.rsp.api.enums.T0;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.secret.report.ReportImpl;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.NonEmptyContent;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.OnContentChange;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.OnWindowClose;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.Periodic;
import it.polimi.deib.sr.rsp.api.secret.time.Times;
import it.polimi.deib.sr.rsp.api.stream.metadata.StreamSchema;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;
import java.time.Instant;

import static it.polimi.deib.sr.rsp.api.engine.config.ConfigurationUtils.*;

/**
 * Created by riccardo on 10/07/2017.
 */
public class EngineConfiguration extends PropertiesConfiguration {

    private static EngineConfiguration config;

    public EngineConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
    }


    public static EngineConfiguration getCurrent() throws ConfigurationException {
        if (config == null)
            return config;
        return getDefault();
    }

    public Report report() {
        Report report = new ReportImpl();
        if (this.onContentChange())
            report.add(new OnContentChange());
        if (this.onWindowClose())
            report.add(new OnWindowClose());
        if (this.nonEmptyContent())
            report.add(new NonEmptyContent());
        if (this.periodic())
            report.add(new Periodic());
        return report;
    }

    public long gett0() {
        switch (t0()) {
            case ZERO:
                return 0;
            case SYSTEM:
            default:
                return Instant.now().toEpochMilli();
        }
    }

    public T0 t0() {
        return T0.valueOf(this.getString(t0, T0.ZERO.name()));
    }

    public String getBaseIRI() {
        return this.getString(BASE_IRI, "http://linkeddata.stream/engine/yasper/");
    }

    public Boolean isUsingEventTime() {
        return Times.EventTime.equals(Times.valueOf(this.getString(TIME, Times.EventTime.name())));

    }

    public Boolean isUsingIngestionTime() {
        return Times.IngestionTime.equals(Times.valueOf(this.getString(TIME, Times.EventTime.name())));

    }

    public String getQueryClass() {
        return this.getString(QUERY_CLASS, ContinuousQuery.class.getCanonicalName());
    }

    public boolean isRecursionEnables() {
        return this.getBoolean(QUERY_RECURSION, false);
    }

    public boolean partialWindowsEnabled() {
        return this.getBoolean(PARTIAL_WINDOW, true);
    }

    public static EngineConfiguration loadConfig(String path) throws ConfigurationException {
        URL resource = EngineConfiguration.class.getResource(path);
        if (config == null) {
            config = new EngineConfiguration(resource.getPath());
        }
        return config;
    }

    public static EngineConfiguration getDefault() throws ConfigurationException {
        return loadConfig("/default.properties");
    }

    public String getBaseURI() {
        return this.getString("rsp_engine.base_uri");
    }

    public StreamSchema getStreamSchema() {
        try {
            return (StreamSchema) Class.forName(this.getString("rsp_engine.stream.item.class")).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return StreamSchema.UNKNOWN;
    }

    public boolean onWindowClose() {
        return this.getBoolean(REPORT_STRATEGY_WC, false);
    }

    public boolean onContentChange() {
        return this.getBoolean(REPORT_STRATEGY_CC, false);
    }

    public boolean nonEmptyContent() {
        return this.getBoolean(REPORT_STRATEGY_NC, false);
    }

    public boolean periodic() {
        return this.getBoolean(REPORT_STRATEGY_PP, false);
    }

    public Tick getTick() {
        return Tick.valueOf(getString(TICK, Tick.TUPLE_DRIVEN.name()));
    }

    public ReportGrain getReportGrain() {
        return ReportGrain.valueOf(getString(REPORT_GRAIN, ReportGrain.SINGLE.name()));
    }

    public Report getReport() {
        Report report = new ReportImpl();

        if (onContentChange())
            report.add(new OnContentChange());
        if (nonEmptyContent())
            report.add(new NonEmptyContent());
        if (onWindowClose())
            report.add(new OnWindowClose());
        if (periodic())
            report.add(new Periodic());

        //TODO remove period from policy

        return report;
    }

    public String getResponseFormat() {
        return this.getString("rsp_engine.response_format");
    }
}
