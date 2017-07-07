package test.experiments;

import it.polimi.rsp.baselines.utils.BaselinesUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.vocabulary.RDF;

/**
 * Created by riccardo on 04/07/2017.
 */
public class MainReasoning {

    public static void main(String[] argvs) {

        Model tbox = ModelFactory.createDefaultModel().read("/Users/riccardo/_Projects/RSP/RSP-Baselines/src/main/resources/arist.tbox.owl");
        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(BaselinesUtils.RHODF_RULE_SET_RUNTIME)).bindSchema(tbox);
        Reasoner reasoner2 = ReasonerRegistry.getOWLReasoner();

        String uri = "http://www.streamreasoning/test/artist#";
        Resource person = ResourceFactory.createResource(uri + "Leonardo");
        Resource type = ResourceFactory.createResource(uri + "Writer");


        Model abox = ModelFactory.createDefaultModel();
        Statement s = abox.createStatement(person, RDF.type, type);
        abox.add(s);


        InfGraph bind = reasoner.bind(abox.getGraph());
        InfModel infModel = ModelFactory.createInfModel(bind);

        infModel.rebind();

        infModel.write(System.out, "TTL");
    }

}
