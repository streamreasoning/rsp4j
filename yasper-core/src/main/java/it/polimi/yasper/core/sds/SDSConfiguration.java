package it.polimi.yasper.core.sds;

import it.polimi.yasper.core.enums.Maintenance;
import lombok.extern.log4j.Log4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;

import static it.polimi.yasper.core.engine.config.ConfigurationUtils.*;

/**
 * Created by riccardo on 10/07/2017.
 */
@Log4j
public class SDSConfiguration extends PropertiesConfiguration {


    public SDSConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
    }


    public boolean hasTboxLocation() {
        return this.containsKey(TBOX_LOCATION);

    }

    public String getTboxLocation() {
        return this.getString(TBOX_LOCATION, "");

    }

    public Maintenance getSdsMaintainance() {
        return Maintenance.valueOf(this.getString(SDS_MAINTAINANCE));

    }

    public Boolean getReasoningActive() {
        return this.getBoolean(REASONING_ACTIVE);

    }

    public String getReasoningRulePath() {
        return this.getString(REASONING_RULE_PATH);

    }

    public String getQueryClass() {
        return this.getString(QUERY_CLASS);
    }

    public boolean isRecursionEnables() {
        return this.getBoolean(QUERY_RECURSION);
    }

    public static SDSConfiguration getDefault() throws ConfigurationException {
        URL resource = SDSConfiguration.class.getResource("/default.properties");
        return new SDSConfiguration(resource.getPath());
    }
}
