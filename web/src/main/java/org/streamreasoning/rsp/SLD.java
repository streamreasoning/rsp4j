package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.streamreasoning.rsp.builders.DistributionBuilder;
import org.streamreasoning.rsp.distribution.WebDataStreamSource;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp.enums.Protocol;
import org.streamreasoning.rsp.enums.Security;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

public class SLD {

//    static JenaRDF rdf = new JenaRDF();

    static RDF rdf = RDFUtils.getInstance();

    public static <T> WebDataStream<T> fetch(String s) {
//        Model read = rdf.createGraph().asJenaModel().read(s);
        //TODO read the rdf graph using jena/rdf4j
        //TODO parse the graph to extract distribution, instantiate a distribution object
        //TODO parse the graph to identify the parser
        Graph description = fetchStreamDescriptor(s);
        Distribution<T>[] distributions = extractDistributions(description);
        Publisher publishers = extractPublisher(description);
        return new WebDataStreamSource<T>(s, description, distributions[0], publishers);
    }

    protected static Graph fetchStreamDescriptor(String uri) {
        return RDFUtils.createGraph();
    }


    protected static <E> Distribution<E>[] extractDistributions(Graph descriptor) {



        return new Distribution[]{new DistributionBuilder().
                access("wss://echo.websocket.org", false) // defines if the distribution uri will be a fragment uri (need a proxy otherwise). (Can be used to change port)
                .publisher(extractPublisher(descriptor))
                .protocol(Protocol.WebSocket)  // Determine what sink to choose
                .security(Security.SSL) // we need to include secure protocol
                .license(License.CC) //mostly for documentation
                .format(Format.STRING).<E>build("colours", false)};
    }

    protected static Publisher extractPublisher(Graph descriptor) {
        return SLD.publisher("http://example.org/mypub");
    }


    public static SLD.Publisher publisher(String s) {
        return new Publisher() {
            @Override
            public IRI uri() {
                return rdf.createIRI(s);
            }

            @Override
            public Graph describe() {
                return null;
            }
        };
    }

    public interface Distribution<T> extends Describable {

        IRI uri();

        void start(ParsingStrategy<T> ps);

        WebDataStream<T> serve();
    }

    public interface WebDataStream<E> extends DataStream<E>, Describable, Named {

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
