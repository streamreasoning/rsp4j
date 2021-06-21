package org.streamreasoning.rsp4j.api.engine.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.streamreasoning.rsp4j.api.enums.ContentFormat;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.T0;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.NonEmptyContent;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnContentChange;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.report.strategies.Periodic;
import org.streamreasoning.rsp4j.api.secret.time.Times;
import org.streamreasoning.rsp4j.api.stream.metadata.StreamSchema;

import java.net.URL;
import java.time.Instant;

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
        return T0.valueOf(this.getString(ConfigurationUtils.t0, T0.ZERO.name()));
    }

    public String getBaseIRI() {
        return this.getString(ConfigurationUtils.BASE_IRI, "http://linkeddata.stream/engine/yasper/");
    }

    public Boolean isUsingEventTime() {
        return Times.EventTime.equals(Times.valueOf(this.getString(ConfigurationUtils.TIME, Times.EventTime.name())));

    }

    public Boolean isUsingIngestionTime() {
        return Times.IngestionTime.equals(Times.valueOf(this.getString(ConfigurationUtils.TIME, Times.EventTime.name())));

    }

    public String getQueryClass() {
        return this.getString(ConfigurationUtils.QUERY_CLASS, ContinuousQuery.class.getCanonicalName());
    }

    public boolean isRecursionEnables() {
        return this.getBoolean(ConfigurationUtils.QUERY_RECURSION, false);
    }

    public boolean partialWindowsEnabled() {
        return this.getBoolean(ConfigurationUtils.PARTIAL_WINDOW, true);
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
        return this.getBoolean(ConfigurationUtils.REPORT_STRATEGY_WC, false);
    }

    public boolean onContentChange() {
        return this.getBoolean(ConfigurationUtils.REPORT_STRATEGY_CC, false);
    }

    public boolean nonEmptyContent() {
        return this.getBoolean(ConfigurationUtils.REPORT_STRATEGY_NC, false);
    }

    public boolean periodic() {
        return this.getBoolean(ConfigurationUtils.REPORT_STRATEGY_PP, false);
    }

    public Tick getTick() {
        return Tick.valueOf(getString(ConfigurationUtils.TICK, Tick.TUPLE_DRIVEN.name()));
    }

    public ReportGrain getReportGrain() {
        return ReportGrain.valueOf(getString(ConfigurationUtils.REPORT_GRAIN, ReportGrain.SINGLE.name()));
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

    public ContentFormat getContentFormat() {
        return ContentFormat.valueOf(this.getString("rsp_engine.content_format"));
    }
}
