package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.log4j.Logger;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Projection implements RelationToRelationOperator<Binding, Binding>, Function<Binding, Binding> {

    private final boolean star;
    private final Var[] vars;
    private final Function<Binding, Binding> f;
    private final Collection<Binding> solutions;
    private Stream<Binding> tvb;
    private static final Logger log = Logger.getLogger(Projection.class);

    public Projection(Stream<Binding> tvb, Var... vars) {
        this.tvb = tvb;
        this.vars = vars;
        this.star = vars == null;
        this.solutions = new ArrayList<>();
    this.f =
        binding -> {
          if (!star) {
            Binding b = new BindingImpl();
            for (Var v : vars) {
              RDFTerm value = binding.value(v);
              if (value != null) b.add(v, value);
              else {
                  log.warn("Projection Variable not found: " + v.name());
                  b.add(v, null);
              }
            }
            return b;
          }
          return binding;
        };
    }

    public Projection(TimeVarying<Collection<Binding>> tvb, Var... vars) {
        this.vars = vars;
        this.star = vars == null;
        this.solutions = tvb.get();
        this.f = binding -> {
            if (!star) {
                Binding b = new BindingImpl();
                for (Var v : vars) {
                    RDFTerm value = binding.value(v);
                    if (value != null)
                        b.add(v, value);
                    else
                        return null;
                }
                return b;
            }
            return binding;
        };
    }

    @Override
    public Stream<Binding> eval(Stream<Binding> sds) {
        return sds.map(f);
    }

    @Override
    public TimeVarying<Collection<Binding>> apply(SDS<Binding> sds) {
        return new TimeVarying<Collection<Binding>>() {
            @Override
            public void materialize(long ts) {
                Stream<Binding> collect = tvb != null ? eval(tvb) : eval(solutions.stream());
                solutions.clear();
                solutions.addAll(collect.collect(Collectors.toList()));
            }

            @Override
            public Collection<Binding> get() {
                return solutions;
            }

            @Override
            public String iri() {
                return null;
            }
        };
    }

    @Override
    public SolutionMapping<Binding> createSolutionMapping(Binding result) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Binding apply(Binding binding) {
        return f.apply(binding);
    }
}
