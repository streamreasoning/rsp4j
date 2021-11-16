package org.streamreasoning.rsp4j.abstraction.projection;

import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;

import java.util.List;
import java.util.stream.Stream;

public class BindingProjection implements Projection<Binding> {
    private org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Projection projection;

    @Override
    public Stream<Binding> project(Stream<Binding> results, List<Var> variables) {
        if(variables.isEmpty()){
            return results;
        }
        if(projection == null){
          this.projection = new org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Projection(
                  Stream.empty(), variables.toArray(new Var[0]));
        }
        return results.map(projection);
    }
}
