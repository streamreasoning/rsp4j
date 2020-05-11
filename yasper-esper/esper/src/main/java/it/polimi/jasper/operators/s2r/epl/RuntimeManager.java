package it.polimi.jasper.operators.s2r.epl;

import com.espertech.esper.client.*;

import java.util.HashMap;


public class RuntimeManager {

    private static EPServiceProvider cep;
    private static EPAdministrator cepAdm;
    private static EPRuntime cepRT;

    public static EPServiceProvider getCEP() {
        if (cep == null) {
            Configuration cep_config = new Configuration();
            cep_config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
            // cep_config.getEngineDefaults().getThreading().setThreadPoolInbound(true);
            cep_config.addEventType("TStream", new HashMap<>());
            String canonicalName = RuntimeManager.class.getCanonicalName();
            cep = EPServiceProviderManager.getProvider(canonicalName, cep_config);
        }
        return cep;
    }


    public static EPAdministrator getAdmin() {
        if (cepAdm == null) {
            cepAdm = cep.getEPAdministrator();
        }
        return cepAdm;
    }

    public static EPRuntime getEPRuntime() {
        if (cepRT == null) {
            cepRT = cep.getEPRuntime();
        }
        return cepRT;
    }

    public static RuntimeManager getInstance() {
        return new RuntimeManager();
    }

}
