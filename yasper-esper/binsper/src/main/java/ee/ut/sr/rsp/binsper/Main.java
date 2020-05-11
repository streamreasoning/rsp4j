package ee.ut.sr.rsp.binsper;

import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.OnWindowClose;
import it.polimi.yasper.core.secret.time.TimeFactory;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.engine.binding.Binding;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Binsper engine = new Binsper(0, null);

        DataStreamImpl<Triple> stream = null;
        WindowNode wo = null;
        SDS<Set<Binding>> sds = null;

        Report report = new ReportImpl();
        report.add(new OnWindowClose());
        boolean eventtime = true;
        String win1 = "win1";



        EsperTripleOpWindowOperator windowOperator = new EsperTripleOpWindowOperator(
                Tick.TIME_DRIVEN,
                report,
                eventtime,
                ReportGrain.SINGLE,
                Maintenance.NAIVE,
                TimeFactory.getInstance(),
                wo,
                sds,
                win1,
                null
        );

        R2ROperatorBinding r2r= new R2ROperatorBinding(null, sds, "http://ceqls.org/");



    }
}
