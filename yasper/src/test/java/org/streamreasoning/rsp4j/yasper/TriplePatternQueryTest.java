package org.streamreasoning.rsp4j.yasper;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TriplePatternQueryTest {


    @Test
    public void testAllVariables() throws InterruptedException {


        ContinuousQuery<Graph, Binding,Graph> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   ?s ?p ?o ." +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP TP = new TP(s, p, o);

        assertEquals(TP,query.r2r());

    }
    @Test
    public void testAllTerms() throws InterruptedException {

        ContinuousQuery<Graph, Binding,Graph> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   <http://test/s> <http://test/p> <http://test/o> ." +
                "}");

        VarOrTerm s = new TermImpl(RDFUtils.createIRI("http://test/s"));
        VarOrTerm p = new TermImpl(RDFUtils.createIRI("http://test/p"));
        VarOrTerm o = new TermImpl(RDFUtils.createIRI("http://test/o"));
        TP TP = new TP(s, p, o);

        assertEquals(TP,query.r2r());

    }
    @Test
    public void testSingleWindow() throws InterruptedException {

        ContinuousQuery<Graph, Binding,Graph> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   <http://test/s> <http://test/p> <http://test/o> ." +
                "}");

        assertEquals(1,query.getWindowMap().size());

        for(Map.Entry<? extends WindowNode, WebStream> entry: query.getWindowMap().entrySet()){
            assertEquals(10*1000,entry.getKey().getRange());
            assertEquals(5*1000,entry.getKey().getStep());
            assertEquals("http://test/window",entry.getKey().iri());
            assertEquals("http://test/stream",entry.getValue().uri());
        }

    }

    @Test
    public void testOutputStream(){
        ContinuousQuery<Graph, Binding,Graph> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   <http://test/s> <http://test/p> <http://test/o> ." +
                "}");

        assertEquals("http://out/stream",query.getOutputStream().uri());
    }
    @Test
    public void testOutputStreamType(){
        ContinuousQuery<Graph, Binding,Graph> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   <http://test/s> <http://test/p> <http://test/o> ." +
                "}");

        assertEquals(true,query.isIstream());
        assertEquals(false,query.isDstream());
        assertEquals(false,query.isRstream());
        query = TPQueryFactory.parse("" +
                "REGISTER DSTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   <http://test/s> <http://test/p> <http://test/o> ." +
                "}");

        assertEquals(false,query.isIstream());
        assertEquals(true,query.isDstream());
        assertEquals(false,query.isRstream());        query = TPQueryFactory.parse("" +
                "REGISTER RSTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   <http://test/s> <http://test/p> <http://test/o> ." +
                "}");

        assertEquals(false,query.isIstream());
        assertEquals(false,query.isDstream());
        assertEquals(true,query.isRstream());    }




}
