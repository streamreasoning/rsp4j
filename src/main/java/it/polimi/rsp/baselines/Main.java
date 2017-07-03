package it.polimi.rsp.baselines;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

/**
 * Created by Riccardo on 08/02/2017.
 */
public class Main {

    public static void main(String[] args) {


        Model ssnBase = FileManager.get().loadModel("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
        Model defaultModel = ModelFactory.createDefaultModel();
        Dataset d = DatasetFactory.create(defaultModel);
        d.getDefaultModel().add(ssnBase);
        d.addNamedModel("Pizza2", FileManager.get().loadModel("http://protege.stanford.edu/ontologies/pizza/pizza.owl"));


        d.getNamedModel("Pizza2").removeAll();

        System.out.println("End");
    }
}
