package org.streamreasoning.rsp4j.api.utils;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class TripleCollector implements Collector<Triple, List<Triple>, Graph> {


    public static TripleCollector toGraph(){
        return new TripleCollector();
    }

    @Override
    public Supplier<List<Triple>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Triple>, Triple> accumulator() {
        return (list, triple)->list.add(triple);
    }

    @Override
    public BinaryOperator<List<Triple>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<Triple>, Graph> finisher() {
        return list ->{
            Graph g = RDFUtils.createGraph();
            list.forEach(t->g.add(t));
            return g;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
