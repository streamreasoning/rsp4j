package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.streamreasoning.rsp.builders.DistributionBuilder;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.streamreasoning.rsp.vocabulary.RDF.pTYPE;
import static org.streamreasoning.rsp.vocabulary.VOCALS.STREAM_ENDPOINT;
import static org.streamreasoning.rsp.vocabulary.VSD.PUBLISHING_SERVICE;

public class SLD {

    public static JenaRDF rdf = new JenaRDF();

    public static Query publisherQuery = QueryFactory.create("SELECT * " +
                                                             "WHERE { " +
                                                             " ?endpoint " + pTYPE + " " + STREAM_ENDPOINT + ";" +
                                                             "" + DCAT.pACCESS + " ?access ; " +
                                                             "" + DCAT.pPROTOCOL + " ?protocol ; " +
                                                             "" + DCAT.pLICENSE + " ?license ; " +
                                                             "" + DCAT.pFORMAT + " ?format . } ");

    public static <T> WebDataStream<T> fetch(String s) {
        Distribution<T>[] distributions = extractDistributions(fetchStreamDescriptor(s));
        return distributions[0].getWebStream();
    }

    public static Graph fetchStreamDescriptor(String uri) {
        return rdf.asGraph(rdf.createGraph().asJenaModel().read(uri, "TTL"));
    }

    public static <E> Distribution<E>[] extractDistributions(Graph descriptor) {

        List<DistributionBuilder> dbs = new ArrayList<>();

        JenaGraph g = (JenaGraph) descriptor;
        ResultSet resultSet = QueryExecutionFactory.create(publisherQuery, g.asJenaModel()).execSelect();

        while (resultSet.hasNext()) {
            Binding binding = resultSet.nextBinding();
            DistributionBuilder d = new DistributionBuilder("");
            d.access(binding.get(Var.alloc("access")).toString(false));
            d.protocol(Protocol.valueOf(binding.get(Var.alloc("protocol")).toString(false)));
            d.format(Format.valueOf(binding.get(Var.alloc("format")).toString(false)));
            String license1 = binding.get(Var.alloc("license")).getURI();
            Arrays.stream(License.values()).forEach(license -> {
                if (license.url().getIRIString().equals(license1))
                    d.license(license);
            });
            d.publisher(extractPublisher(descriptor));
            dbs.add(d);
        }


        return dbs.stream().map(db -> db.buildSource(descriptor)).collect(Collectors.toList()).toArray(new SLD.Distribution[dbs.size()]);
    }

    public static Publisher extractPublisher(Graph descriptor) {

        Query publisherQuery = QueryFactory.create("SELECT ?publisher  WHERE {?publisher a " + PUBLISHING_SERVICE + " } ");
        JenaGraph g = (JenaGraph) descriptor;
        ResultSet resultSet = QueryExecutionFactory.create(publisherQuery, g.asJenaModel()).execSelect();

        while (resultSet.hasNext()) {
            Var publisher = Var.alloc("publisher");
            return SLD.publisher(resultSet.nextBinding().get(publisher).getURI());
        }
        return null;
    }


    public static SLD.Publisher publisher(String s) {
        return new PublisherImpl(rdf.createIRI(s), rdf.createGraph());
    }


    public interface Distribution<T> extends Describable {

        IRI uri();

        WebDataStream<T> start(ParsingStrategy<T> ps);

        WebDataStream<T> serve();

        WebDataStream<T> getWebStream();
    }


    public interface WebStream<E> extends Describable, Named {

        SLD.Publisher publisher();

        WebDataStream<E> serve();

    }


    public interface WebDataStream<E> extends DataStream<E>, Named, Describable {

        SLD.Publisher publisher();

        SLD.Distribution<E>[] distribution();

    }


    public interface Publisher extends Describable {

        IRI uri();

    }


    protected interface Describable {

        Graph describe();

    }


    protected interface Named {

        IRI uri();

    }

}
