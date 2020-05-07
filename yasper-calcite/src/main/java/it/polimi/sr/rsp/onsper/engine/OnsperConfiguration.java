package it.polimi.sr.rsp.onsper.engine;

import it.polimi.yasper.core.engine.config.EngineConfiguration;
import org.apache.commons.configuration.ConfigurationException;

public class OnsperConfiguration extends EngineConfiguration {
    public OnsperConfiguration(String fileName) throws ConfigurationException {
        super(fileName);
    }

    public String getJDBCDriver() {
        return this.getString("jdbc.driver");
    }

    public String getJDBCURL() {
        return this.getString("jdbc.url");
    }

    public String getJDBCUSER() {
        return this.getString("jdbc.user");
    }

    public String getJDBCPassword() {
        return this.getString("jdbc.password");
    }

}


