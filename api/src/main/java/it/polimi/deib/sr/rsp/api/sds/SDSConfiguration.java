package it.polimi.deib.sr.rsp.api.sds;

import it.polimi.deib.sr.rsp.api.engine.config.ConfigurationUtils;
import it.polimi.deib.sr.rsp.api.enums.Maintenance;
import lombok.extern.log4j.Log4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;

/**
 * Created by riccardo on 10/07/2017.
 */
@Log4j
public class SDSConfiguration extends PropertiesConfiguration {


    public SDSConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
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

    public static SDSConfiguration getDefault() throws ConfigurationException {
        URL resource = SDSConfiguration.class.getResource("/default.properties");
        return new SDSConfiguration(resource.getPath());
    }
}
