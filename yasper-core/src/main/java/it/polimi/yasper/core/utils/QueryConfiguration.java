package it.polimi.yasper.core.utils;

import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import static it.polimi.yasper.core.utils.ConfigurationUtils.*;

/**
 * Created by riccardo on 10/07/2017.
 */
public class QueryConfiguration extends PropertiesConfiguration {

    public QueryConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
    }

    public String getTboxLocation() {
        return this.getString(TBOX_LOCATION);

    }

    public Maintenance getSdsMaintainance() {
        return Maintenance.valueOf(this.getString(SDS_MAINTAINANCE));

    }

    public Boolean getReasoningActive() {
        return this.getBoolean(REASONING_ACTIVE);

    }

    public Entailment getReasoningEntailment() {
        return Entailment.valueOf(this.getString(REASONING_ENTAILMENT));

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
}
