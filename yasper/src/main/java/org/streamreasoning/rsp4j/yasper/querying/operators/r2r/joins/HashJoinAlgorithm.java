package org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins;

import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Var;

import java.util.*;
import java.util.stream.Collectors;

public class HashJoinAlgorithm implements JoinAlgorithm<Binding> {
  @Override
  public Set<Binding> join(Set<Binding> bindings1, Set<Binding> bindings2) {
    Set<Binding> results = new HashSet<Binding>();
    List<Var> joinVars =
        findOverlapingVars(bindings1, bindings2);
    if (!joinVars.isEmpty()) {

      Set<Binding> left = bindings1.size() > bindings2.size() ? bindings1 : bindings2;
      Set<Binding> right = bindings1.size() > bindings2.size() ? bindings2 : bindings1;
      for (Var joinVar : joinVars) {
        // create hash map
        Map<RDFTerm, List<Binding>> bindingsHash = new HashMap<>();
        for (Binding binding : right) {
          bindingsHash.computeIfAbsent(binding.value(joinVar), k -> new ArrayList<>()).add(binding);
        }

        // do the joins
        for (Binding binding : left) {
          Set<Binding> bound =
              bindingsHash.getOrDefault(binding.value(joinVar), Collections.emptyList()).stream()
                  .map(b -> b.union(binding))
                  .collect(Collectors.toSet());

          results.addAll(bound);
        }
        right = results; // we assign the right value the results when multiple variables need to be bound
      }
    }
    return results;
  }

  private List<Var> findOverlapingVars(Set<Binding> b1, Set<Binding> b2) {
    if(b1.isEmpty() || b2.isEmpty()){
      return Collections.emptyList();
    } else {
      Set<Var> overlappingVars = new HashSet<>(b1.iterator().next().variables());
      overlappingVars.retainAll(b2.iterator().next().variables());
      return new ArrayList<>(overlappingVars);
    }
  }
}
