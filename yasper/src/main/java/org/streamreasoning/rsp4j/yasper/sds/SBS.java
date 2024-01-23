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

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Quad;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.*;
import java.util.stream.Stream;

/**
 * A simple, memory-based implementation of Dataset.
 * <p>
 * {@link Quad}s in the graph are kept in a {@link Set}.
 * <p>
 * All Stream operations are performed using parallel and unordered directives.
 */
final public class SBS implements SDS<Binding> {

    private static final int TO_STRING_MAX = 10;
    private final Set<Binding> quads = new HashSet<>();
    private final Map<IRI, TimeVarying<Binding>> tvgs = new HashMap<>();
    private final IRI def;
    private boolean materialized;

    public SBS() {
        this.def = RDFUtils.createIRI("def");
        this.materialized = false;
    }


    @Override
    public Collection<TimeVarying<Binding>> asTimeVaryingEs() {
        return tvgs.values();
    }

    @Override
    public void add(IRI iri, TimeVarying<Binding> tvg) {
        tvgs.put(iri, tvg);
    }

    @Override
    public void add(TimeVarying<Binding> tvg) {
        tvgs.put(def, tvg);
    }

    @Override
    public void materialized() {
        this.materialized = true;
    }

    @Override
    public Stream<Binding> toStream() {
        if (materialized) {
            materialized = false;
            return tvgs.values().stream().map(TimeVarying::get);
        } else throw new RuntimeException("SDS not materialized");

    }
}
