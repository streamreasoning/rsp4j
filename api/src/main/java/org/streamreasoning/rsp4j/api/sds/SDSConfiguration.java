package org.streamreasoning.rsp4j.api.sds;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.streamreasoning.rsp4j.api.engine.config.ConfigurationUtils;
import org.streamreasoning.rsp4j.api.enums.Maintenance;

import java.net.URL;

/**
 * Created by riccardo on 10/07/2017.
 */
public class SDSConfiguration extends PropertiesConfiguration {


    public SDSConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
    }

    public static SDSConfiguration getDefault() throws ConfigurationException {
        URL resource = SDSConfiguration.class.getResource("/default.properties");
        return new SDSConfiguration(resource.getPath());
    }

    public boolean hasTboxLocation() {
        return this.containsKey(ConfigurationUtils.TBOX_LOCATION);

    }

    public String getTboxLocation() {
        return this.getString(ConfigurationUtils.TBOX_LOCATION, "");

    }

    public Maintenance getSdsMaintainance() {
        return Maintenance.valueOf(this.getString(ConfigurationUtils.SDS_MAINTAINANCE));

    }

    public Boolean getReasoningActive() {
        return this.getBoolean(ConfigurationUtils.REASONING_ACTIVE);

    }

    public String getReasoningRulePath() {
        return this.getString(ConfigurationUtils.REASONING_RULE_PATH);

    }

    public String getQueryClass() {
        return this.getString(ConfigurationUtils.QUERY_CLASS);
    }

    public boolean isRecursionEnables() {
        return this.getBoolean(ConfigurationUtils.QUERY_RECURSION);
    }
}
