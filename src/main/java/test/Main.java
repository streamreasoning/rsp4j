package test;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import it.polimi.rsp.baselines.rsp.stream.item.jena.GraphStimulus;

/**
 * Created by riccardo on 04/07/2017.
 */
public class Main {

    public static void main(String[] argvs) {

        String incremental_query = "select irstream * from stream1.win:time_batch( 5 msec )";
        Configuration configuration = new Configuration();
        configuration.addEventType("stream1", new GraphStimulus());

        EPServiceProvider provider = EPServiceProviderManager.getProvider("", configuration);
        EPStatementObjectModel epStatementObjectModel = provider.getEPAdministrator().compileEPL(incremental_query);

        epStatementObjectModel.getAnnotations();
        System.out.print("ciao");
    }

}
