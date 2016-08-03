package it.polimi.rsp.baselines.utils;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public final class RDFSUtils {

    public static final String UNIV_BENCH_RDFS = "src/main/resources/data/inference/univ-bench-rdfs-without-datatype-materialized.rdfs";
    public static final String RDFRESOURCE = "http://www.w3.org/2000/01/rdf-schema#Resource";
    public static final String TYPE_PROPERTY = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String[] TYPE_PROPERTY_ARR = new String[]{"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"};

    public static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    public static final String[] DATATYPEPROPERTIES = {"http://swat.cse.lehigh.edu/onto/univ-bench.owl#age",
            "http://swat.cse.lehigh.edu/onto/univ-bench.owl#name", "http://swat.cse.lehigh.edu/onto/univ-bench.owl#emailAddress",

            "http://swat.cse.lehigh.edu/onto/univ-bench.owl#officeNumber", "http://swat.cse.lehigh.edu/onto/univ-bench.owl#title",
            "http://swat.cse.lehigh.edu/onto/univ-bench.owl#telephone", "http://swat.cse.lehigh.edu/onto/univ-bench.owl#researchInterest"};

    public static final String[] EXCLUDED_PROPERTIES = {"http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#tenured",
            "http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#softwareVersion",
            "http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationDate",};

    public static final String[] SCHEMAPROPERTIES = {"http://www.w3.org/1999/02/22-rdf-syntax-ns#first",
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#object", "http://www.w3.org/1999/02/22-rdf-syntax-ns#subject",
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate", "http://www.w3.org/1999/02/22-rdf-syntax-ns#rest",
            "http://www.w3.org/2000/01/rdf-schema#isDefinedBy", "http://www.w3.org/2000/01/rdf-schema#seeAlso",
            "http://www.w3.org/2000/01/rdf-schema#seeAlso", "http://www.w3.org/2000/01/rdf-schema#subClassOf",
            "http://www.w3.org/2000/01/rdf-schema#domain", "http://www.w3.org/2000/01/rdf-schema#subPropertyOf",
            "http://www.w3.org/2000/01/rdf-schema#range", "http://www.w3.org/2000/01/rdf-schema#comment",
            "http://www.w3.org/2000/01/rdf-schema#label", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Property",
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#List", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement",
            "http://www.w3.org/2000/01/rdf-schema#Literal", "http://www.w3.org/2000/01/rdf-schema#Datatype",
            "http://www.w3.org/2000/01/rdf-schema#Container", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq",
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt",
            "http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty", "http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral"};
    public static final String[] SCHEMACLASSES = {"http://www.w3.org/1999/02/22-rdf-syntax-ns#Property",
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#List", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement",
            "http://www.w3.org/2000/01/rdf-schema#Literal", "http://www.w3.org/2000/01/rdf-schema#Datatype",
            "http://www.w3.org/2000/01/rdf-schema#Container", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq",
            "http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag", "http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt",
            "http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty", "http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral"};

    public static boolean isDatatype(String s) {
        for (String p : DATATYPEPROPERTIES) {
            if (p.equals(s))
                return true;
        }
        return false;
    }

    public static boolean isSchema(String s) {
        for (String p : SCHEMAPROPERTIES) {
            if (p.equals(s))
                return true;
        }
        return false;
    }

    public static boolean isType(String s) {
        return " <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>".equals(s);
    }

    public static boolean isSchemaClass(String s) {
        for (String p : SCHEMACLASSES) {
            if (p.equals(s))
                return true;
        }
        return false;
    }

    public static Model loadModel(String path) {
        FileManager.get().addLocatorClassLoader(RDFSUtils.class.getClassLoader()); //TODO was Baselines class loader
        return FileManager.get().loadModel(path, null, "RDF/XML");
    }
}
