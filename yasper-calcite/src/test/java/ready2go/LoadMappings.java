package ready2go;

import com.google.inject.Injector;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.impl.R2RMLMappingManagerImpl;
import it.unibz.inf.ontop.exception.DuplicateMappingException;
import it.unibz.inf.ontop.exception.InvalidMappingException;
import it.unibz.inf.ontop.exception.MappingIOException;
import it.unibz.inf.ontop.injection.OntopMappingSQLAllConfiguration;
import it.unibz.inf.ontop.spec.mapping.parser.SQLMappingParser;
import it.unibz.inf.ontop.spec.mapping.pp.SQLPPMapping;
import it.unibz.inf.ontop.spec.mapping.serializer.SQLPPMappingToR2RMLConverter;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;

import java.io.File;
import java.net.URL;
import java.util.Collection;

public class LoadMappings {
   static RDF4J rdf = new RDF4J();

    public static void main(String[] args) throws MappingIOException, DuplicateMappingException, InvalidMappingException {
        URL calcite = LoadMappings.class.getResource("/calcite.properties");
        URL books = LoadMappings.class.getResource("/exampleBooks.obda");

        OntopMappingSQLAllConfiguration configuration = OntopMappingSQLAllConfiguration.defaultBuilder()
                .propertyFile(calcite.getPath())
                .build();
        Injector injector = configuration.getInjector();

        SQLMappingParser mappingParser = injector.getInstance(SQLMappingParser.class);

        SQLPPMapping parse = mappingParser.parse(new File(books.getFile()));

        SQLPPMappingToR2RMLConverter converter = new SQLPPMappingToR2RMLConverter(parse, null);

        R2RMLMappingManagerImpl mm = new R2RMLMappingManagerImpl(rdf);

        Collection<TriplesMap> tripleMaps = converter.getTripleMaps();

        Graph graph1 = mm.exportMappings(tripleMaps);

    }

}
