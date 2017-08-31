package it.polimi.yasper.core.utils;

import it.polimi.yasper.core.enums.Time;
import it.polimi.yasper.core.query.ContinuousQuery;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;

import static it.polimi.yasper.core.utils.ConfigurationUtils.*;

/**
 * Created by riccardo on 10/07/2017.
 */
public class EngineConfiguration extends PropertiesConfiguration {

    public EngineConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
    }

    public Boolean isUsingEventTime() {
        return Time.EventTime.equals(Time.valueOf(this.getString(TIME, Time.EventTime.name())));

    }

    public Boolean isUsingIngestionTime() {
        return Time.IngestionTime.equals(Time.valueOf(this.getString(TIME, Time.EventTime.name())));

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

    public static EngineConfiguration getDefault() {
        URL resource = EngineConfiguration.class.getResource("/default.properties");
        try {
            return new EngineConfiguration(resource.getPath());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getBaseURI() {
        return this.getString("rsp_engine.base_uri");
    }
}
