package it.polimi.deib.sr.rsp.api.engine.config;

import lombok.extern.log4j.Log4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Iterator;

/**
 * Created by riccardo on 09/07/2017.
 */
@Log4j
public class ConfigurationUtils {

    public static final String ENGINE = "rsp_engine.";
    public static final String QUERY = "rsp_query.";
    public static final String QUERY_CLASS = ENGINE + "query_class";
    public static final String TBOX_LOCATION = ENGINE + "tbox_location";
    public static final String SDS_MAINTAINANCE = ENGINE + "sds.mantainance";
    public static final String QUERY_RECURSION = ENGINE + "query.recursion";
    public static final String REASONING_ACTIVE = ENGINE + "reasoning.active";
    public static final String REASONING_ENTAILMENT = ENGINE + "reasoning.entailment";
    public static final String REASONING_RULE_PATH = ENGINE + "reasoning.rulepath";
    public static final String TIME = ENGINE + "time";
    public static final String PARTIAL_WINDOW = ENGINE + "partialwindow";
    public static final String BASE_IRI = ENGINE + "base_iri";

    public static final String REPORT_STRATEGY_WC = ENGINE + "on_window_close";
    public static final String REPORT_STRATEGY_CC = ENGINE + "on_content_change";
    public static final String REPORT_STRATEGY_NC = ENGINE + "non_empty_content";
    public static final String REPORT_STRATEGY_PP = ENGINE + "periodic";

    public static final String TICK = ENGINE + "tick";
    
    public static final String REPORT_GRAIN = ENGINE + "report_grain";


//    rspengine.tbox_location=/Users/riccardo/_Projects/RSP/RSP-Baselines/src/main/resources/arist.tbox.owl
//    rspengine.graph_mantainance=naive
//    rspengine.reasoning=true
//    rspengine.reasoning.entailment=rhodf
//    rspengine.reasoning.rule_path=/Users/riccardo/_Projects/RSP/yasper/yasper-jena/src/main/resources/jena/owl.rules

    private static ConfigurationUtils instance = null;
    private static Configuration config = null;

    private ConfigurationUtils(String propertiesFilePath) {
        try {
            config = new PropertiesConfiguration(propertiesFilePath);
            Iterator<String> iterator = config.getKeys();
            while (iterator.hasNext()) {
                String property = iterator.next();
                String sysValue = System.getProperty(property);
                if (sysValue != null) {
                    config.setProperty(property, sysValue);
                }
            }
        } catch (ConfigurationException e) {
            log.error("Error while reading the configuration file", e);
        }
    }

    public static void initialize(String propertiesFilePath) {
        instance = new ConfigurationUtils(propertiesFilePath);
    }

    public static ConfigurationUtils getInstance() {
        if (instance == null) {
            log.info("ConfigurationUtils not yet initialized!");
        }
        return instance;
    }


}
