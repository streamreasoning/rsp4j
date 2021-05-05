package org.streamreasoning.rsp4j.abstraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.examples.RDFTripleStream;

import java.util.HashSet;
import java.util.Set;

public class Task {
    private Set<RDFStream> inputStreams;
    private Set<S2RContainer> s2rs;
    private R2RContainer r2r;
    private R2SContainer r2s;
    private RDFTripleStream outputStream;
    private Report report;
    private SDS sds;
    private ContinuousQuery query;


    public Task(TaskBuilder builder) {
        this.inputStreams = builder.inputStreams;
        this.s2rs = builder.s2rs;
        this.r2r = builder.r2r;
        this.r2s = builder.r2s;
        this.outputStream = builder.outputStream;
        this.report = builder.report;
        this.sds = builder.sds;
        this.query = builder.query;
    }

    /**
     * Retrieves registered inputstream for a given uri
     * @param uri
     * @return
     */
    public RDFStream getInputStream(String uri){
        for(RDFStream stream: inputStreams){
            if(stream.uri().equals(uri)){
                return stream;
            }
        }
        return null;
    }
    public SDS getSDS(){
        return sds;
    }
    public ContinuousQuery getQuery(){
        return query;
    }
    public Set<RDFStream> getInputStreams() {
        return inputStreams;
    }

    public Set<S2RContainer> getS2Rs() {
        return s2rs;
    }

    public R2RContainer getR2R() {
        return r2r;
    }

    public R2SContainer getR2S() {
        return r2s;
    }

    public RDFTripleStream getOutputStream() {
        return outputStream;
    }

    public Report getReport() {
        return report;
    }

    public static class TaskBuilder {
        private Set<RDFStream> inputStreams;
        private Set<S2RContainer> s2rs;
        private R2RContainer r2r;
        private R2SContainer r2s;
        private RDFTripleStream outputStream;
        private Report report;
        private SDS sds;
        private ContinuousQuery query;


        public TaskBuilder() {
            this.inputStreams = new HashSet<RDFStream>();
            this.s2rs = new HashSet<S2RContainer>();
        }

        public TaskBuilder addSource(RDFStream stream) {
            this.inputStreams.add(stream);
            return this;
        }

        public TaskBuilder addS2R(String sourceURI, StreamToRelationOperatorFactory s2rFactory, String tvgName) {
            s2rs.add(new S2RContainer(sourceURI, s2rFactory, tvgName));
            return this;
        }

        public TaskBuilder addR2R(String tvgName, RelationToRelationOperator r2rFactory) {
            this.r2r = new R2RContainer(tvgName, r2rFactory);
            return this;
        }

        public TaskBuilder addR2S(String sinkURI, RelationToStreamOperator r2sFactory) {
            this.r2s = new R2SContainer(sinkURI, r2sFactory);
            return this;
        }

        public TaskBuilder addSink(RDFTripleStream stream) {
            this.outputStream = stream;
            return this;
        }

        //TODO refacture and remove (part of R2R)?
        public TaskBuilder addSDS(SDS sds) {
            this.sds = sds;
            return this;
        }

        //TODO refacture and remove (part of R2R)?
        public TaskBuilder addContinuousQuery(ContinuousQuery query) {
            this.query = query;
            return this;
        }

        public TaskBuilder addReporting(Report report) {
            this.report = report;
            return this;
        }

        public Task build() {
            return new Task(this);
        }


    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class S2RContainer {
        @Getter
        private String sourceURI;
        @Getter
        private StreamToRelationOperatorFactory s2rFactory;
        @Getter
        private String tvgName;
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2RContainer {
        @Getter
        private String tvgName;
        @Getter
        private RelationToRelationOperator r2rFactory;

    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2SContainer {
        @Getter
        private String sinkURI;
        @Getter
        private RelationToStreamOperator r2rFactory;

    }
}
