/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.streamreasoning.rsp4j.yasper.sds;

import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import lombok.AllArgsConstructor;
import org.apache.commons.rdf.api.*;
import org.apache.commons.rdf.simple.DatasetGraphView;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple, memory-based implementation of Dataset.
 * <p>
 * {@link Quad}s in the graph are kept in a {@link Set}.
 * <p>
 * All Stream operations are performed using parallel and unordered directives.
 */
final public class SDSImpl implements Dataset, SDS<Graph> {

    private static final int TO_STRING_MAX = 10;
    private final Set<Quad> quads = new HashSet<>();
    private final Set<TimeVarying<Graph>> defs = new HashSet<>();
    private final Map<IRI, TimeVarying<Graph>> tvgs = new HashMap<>();
    private final IRI def;


    public SDSImpl() {
        this.def = RDFUtils.createIRI("def");
    }


    @Override
    public void add(final BlankNodeOrIRI graphName, final BlankNodeOrIRI subject, final IRI predicate, final RDFTerm object) {
        final BlankNodeOrIRI newGraphName = (BlankNodeOrIRI) internallyMap(graphName);
        final BlankNodeOrIRI newSubject = (BlankNodeOrIRI) internallyMap(subject);
        final IRI newPredicate = (IRI) internallyMap(predicate);
        final RDFTerm newObject = internallyMap(object);
        final Quad result = RDFUtils.createQuad(newGraphName, newSubject, newPredicate, newObject);
        quads.add(result);
    }

    @Override
    public void add(final Quad quad) {
        final BlankNodeOrIRI newGraph = (BlankNodeOrIRI) internallyMap(quad.getGraphName().orElse(null));
        final BlankNodeOrIRI newSubject = (BlankNodeOrIRI) internallyMap(quad.getSubject());
        final IRI newPredicate = (IRI) internallyMap(quad.getPredicate());
        final RDFTerm newObject = internallyMap(quad.getObject());
        // Check if any of the object references changed during the mapping, to
        // avoid creating a new Quad object if possible
        if (newGraph == quad.getGraphName().orElse(null) && newSubject == quad.getSubject()
                && newPredicate == quad.getPredicate() && newObject == quad.getObject()) {
            quads.add(quad);
        } else {
            // Make a new Quad with our mapped instances
            final Quad result = RDFUtils.createQuad(newGraph, newSubject, newPredicate, newObject);
            quads.add(result);
        }
    }

    private <T extends RDFTerm> RDFTerm internallyMap(final T object) {
        if (object == null) {
            return object;
        } else if (object instanceof BlankNode) {
            final BlankNode blankNode = (BlankNode) object;
            // This guarantees that adding the same BlankNode multiple times to
            // this graph will generate a local object that is mapped to an
            // equivalent object, based on the code in the package private
            // BlankNodeImpl class
            return RDFUtils.createBlankNode(blankNode.uniqueReference());
        } else if (object instanceof IRI) {
            final IRI iri = (IRI) object;
            return RDFUtils.createIRI(iri.getIRIString());
        } else if (object instanceof Literal) {
            final Literal literal = (Literal) object;
            if (literal.getLanguageTag().isPresent()) {
                return RDFUtils.createLiteral(literal.getLexicalForm(), literal.getLanguageTag().get());
            }
            return RDFUtils.createLiteral(literal.getLexicalForm(), (IRI) internallyMap(literal.getDatatype()));
        } else {
            throw new IllegalArgumentException("Not a BlankNode, IRI or Literal: " + object);
        }
    }

    @Override
    public void clear() {
        quads.clear();
    }

    @Override
    public boolean contains(final Optional<BlankNodeOrIRI> graphName, final BlankNodeOrIRI subject, final IRI predicate, final RDFTerm object) {
        return stream(graphName, subject, predicate, object).findAny().isPresent();
    }

    @Override
    public boolean contains(final Quad quad) {
        return quads.contains(Objects.requireNonNull(quad));
    }

    @Override
    public Stream<Quad> stream() {
        return quads.parallelStream().unordered();
    }

    @Override
    public Stream<Quad> stream(final Optional<BlankNodeOrIRI> graphName, final BlankNodeOrIRI subject, final IRI predicate,
                               final RDFTerm object) {
        final Optional<BlankNodeOrIRI> newGraphName;
        if (graphName == null) {
            // Avoid Optional<Optional<BlankNodeOrIRI>> ...
            newGraphName = null;
        } else {
            newGraphName = graphName.map(g -> (BlankNodeOrIRI) internallyMap(g));
        }
        final BlankNodeOrIRI newSubject = (BlankNodeOrIRI) internallyMap(subject);
        final IRI newPredicate = (IRI) internallyMap(predicate);
        final RDFTerm newObject = internallyMap(object);

        return getQuads(t -> {
            if (newGraphName != null && !t.getGraphName().equals(newGraphName)) {
                // This would check Optional.empty() == Optional.empty()
                return false;
            }
            if (subject != null && !t.getSubject().equals(newSubject)) {
                return false;
            }
            if (predicate != null && !t.getPredicate().equals(newPredicate)) {
                return false;
            }
            if (object != null && !t.getObject().equals(newObject)) {
                return false;
            }
            return true;
        });
    }

    private Stream<Quad> getQuads(final Predicate<Quad> filter) {
        return stream().filter(filter);
    }

    @Override
    public void remove(final Optional<BlankNodeOrIRI> graphName, final BlankNodeOrIRI subject, final IRI predicate, final RDFTerm object) {
        final Stream<Quad> toRemove = stream(graphName, subject, predicate, object);
        for (final Quad t : toRemove.collect(Collectors.toList())) {
            // Avoid ConcurrentModificationException in ArrayList
            remove(t);
        }
    }

    @Override
    public void remove(final Quad quad) {
        quads.remove(Objects.requireNonNull(quad));
    }

    @Override
    public long size() {
        return quads.size();
    }

    @Override
    public String toString() {
        final String s = stream().limit(TO_STRING_MAX).map(Object::toString).collect(Collectors.joining("\n"));
        if (size() > TO_STRING_MAX) {
            return s + "\n# ... +" + (size() - TO_STRING_MAX) + " more";
        }
        return s;
    }

    @Override
    public void close() {
    }

    @Override
    public Graph getGraph() {
        return getGraph(null).get();
    }

    @Override
    public Optional<Graph> getGraph(final BlankNodeOrIRI graphName) {
        return Optional.of(new DatasetGraphView(this, graphName));
    }

    @Override
    public Stream<BlankNodeOrIRI> getGraphNames() {
        // Not very efficient..
        return stream().map(Quad::getGraphName).filter(Optional::isPresent).map(Optional::get).distinct();
    }

    @Override
    public Collection<TimeVarying<Graph>> asTimeVaryingEs() {
        return defs;
    }

    @Override
    public void add(IRI iri, TimeVarying<Graph> tvg) {
        tvgs.put(iri, tvg);
    }

    @Override
    public void add(TimeVarying<Graph> tvg) {
        defs.add(tvg);
    }

    @Override
    public SDS<Graph> materialize(final long ts) {
        //TODO here applies the consolidation strategies
        //Default consolidation coaleces all the current
        //content graphs and produces the SDS to who execute the query.

        //I need to re-add all the triples because the dataset works on quads
        //Altenrativelt one can wrap it into a graph interface and update it directly within the tvg
        // this way there's no need to readd after materialization

        this.clear();

        defs.stream().map(g -> {
            g.materialize(ts);
            return (Graph) g.get();
        }).flatMap(Graph::stream)
                .forEach(t -> this.add(def, t.getSubject(), t.getPredicate(), t.getObject()));

        tvgs.entrySet().stream()
                .map(e -> {
                    e.getValue().materialize(ts);
                    return new NamedGraph(e.getKey(), e.getValue().get());
                }).forEach(n -> n.g.stream()
                .forEach(o -> this.add(n.name, o.getSubject(), o.getPredicate(), o.getObject())));

        return this;
    }


    @AllArgsConstructor
    class NamedGraph {
        public IRI name;
        public Graph g;
    }

}
