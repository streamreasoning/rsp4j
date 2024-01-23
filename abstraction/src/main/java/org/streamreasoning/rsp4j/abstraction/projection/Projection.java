package org.streamreasoning.rsp4j.abstraction.projection;

import org.streamreasoning.rsp4j.api.operators.r2r.Var;

import java.util.List;
import java.util.stream.Stream;

public interface Projection<R> {

    Stream<R> project(Stream<R> results, List<Var> variables);
}
