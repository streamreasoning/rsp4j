package org.streamreasoning.rsp4j.yasper.querying;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.api.sds.DataSet;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.JoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.sds.DataSetImpl;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StaticR2RTest {

  public void testStatic() {
    VarOrTerm s = new VarImpl("green");
    VarOrTerm s2 = new VarImpl("red");
    VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    VarOrTerm p2 = new TermImpl("http://color#source");
    VarOrTerm o = new TermImpl("http://color#Green");

    VarOrTerm o2 = new TermImpl("http://color#Red");
    VarOrTerm o3 = new VarImpl("source");
    TP tp = new TP(s, p, o);
    TP tp2 = new TP(s2, p, o2);
    TP tp3 = new TP(s, p2, o3);
    TP tp4 = new TP(s2, p2, o3);
    BGP bgp = BGP.createFrom(tp).join(tp2).join(tp3).join(tp4).create();
    URL fileURL = StaticR2RTest.class.getClassLoader().getResource(
            "colors.nt");

    DataSet<Graph> staticDataSet = new DataSetImpl("default", fileURL.getPath(), RDFBase.NT);
    // create a graph
    Stream<Graph> g = staticDataSet.getContent().stream();

    Stream<Binding> bindings = bgp.eval(g);

    Binding expected = new BindingImpl();
    expected.add(new VarImpl("red"), RDFUtils.createIRI("S7"));

    expected.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
    expected.add(new VarImpl("source"), RDFUtils.createIRI("Source1"));

    assertEquals(createSet(expected), bindings.collect(Collectors.toSet()));
    }


  public void testStaticCityBench() throws InterruptedException {
    VarOrTerm s = new VarImpl("p1");
    VarOrTerm s2 = new VarImpl("p2");
    VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    VarOrTerm o = new TermImpl("http://www.insight-centre.org/citytraffic#CongestionLevel");

    TP tp = new TP(s, p, o);
    TP tp2 = new TP(s2, p, o);

    BGP bgp = BGP.createFrom(tp).join(tp2).create();
    Stream<Binding> bindings = Stream.empty();
    while (true) {
      DataSet<Graph> staticDataSet =
          new DataSetImpl(
              "default",
              "/Users/psbonte/Documents/Github/CityBench_yasper_tmp/dataset/SensorRepository.n3",
              RDFBase.TTL);
      // create a graph
      Stream<Graph> g = staticDataSet.getContent().stream();
      long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

      bindings = bgp.eval(g);
      long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      long actualMemUsed = afterUsedMem - beforeUsedMem;
      System.out.println(actualMemUsed);
      System.out.println(bindings.collect(Collectors.toSet()).size());
      Thread.sleep(1000);
    }



  }

  public void testStaticCityBench2() throws InterruptedException {
    VarOrTerm s = new VarImpl("p1");
    VarOrTerm s2 = new VarImpl("p2");
    VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    VarOrTerm o = new TermImpl("http://www.insight-centre.org/citytraffic#CongestionLevel");

    TP tp = new TP(s, p, o);
    TP tp2 = new TP(s2, p, o);



      DataSet<Graph> staticDataSet =
              new DataSetImpl(
                      "default",
                      "/Users/psbonte/Documents/Github/CityBench_yasper_tmp/dataset/SensorRepository.n3",
                      RDFBase.TTL);
    // create a graph
    Set<Binding> results = Collections.emptySet();
    Map<Var,List<RDFTerm>> table = new HashMap<>();
    while (true) {
      Collection<Graph> g = staticDataSet.getContent();
      Set<Binding> b1 = tp.eval(g.stream()).collect(Collectors.toSet());
      Set<Binding> b2 = tp2.eval(g.stream()).collect(Collectors.toSet());
      JoinAlgorithm<Binding> join = new HashJoinAlgorithm();

      results = join.join(b1, b2);
      System.out.println(results.size());
      Thread.sleep(1000);
      table = new HashMap<>();
      for(Binding b: results){
        for(Var var: b.variables()){
        if(!table.containsKey(var)){
          table.put(var, new ArrayList<>());
          }
          table.get(var).add(b.value(var));
        }
      }
      System.out.println(table.size());
  }


  }
    private Set<Binding> createSet(Binding... bindings){
        return new HashSet<>(Arrays.asList(bindings));
    }


}
