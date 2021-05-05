package org.streamreasoning.rsp4j.abstraction;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.ContinuousQueryExecutionImpl;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.R2RImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
@Log4j
public class ContinuousProgram {

    private Task task;
    private Rule rule;

    public ContinuousProgram(ContinuousProgramBuilder builder){
        this.task = builder.task;
        this.rule = builder.rule;
    }
    public ContinuousQueryExecution getContinuousQueryExecution(){
        // link the input streams to the S2R operators
        for(Task.S2RContainer s2r :task.getS2Rs()){
            String streamURI = s2r.getSourceURI();
            String tvgName = s2r.getTvgName();
            IRI iri = RDFUtils.createIRI(streamURI);
            RDFStream inputStream = task.getInputStream(streamURI);
            if(inputStream!=null) {
                TimeVarying<Graph> tvg = s2r.getS2rFactory().apply(inputStream, iri);
                if (tvg.named()) {
                    task.getSDS().add(iri, tvg);
                } else {
                    task.getSDS().add(tvg);
                }
            }else{
                log.error(String.format("No stream found for IRI %s",streamURI));
            }
        }
        ContinuousQueryExecution<Graph, Graph, Triple> cqe = new ContinuousQueryExecutionImpl<Graph, Graph, Triple>(task.getSDS(), task.getQuery(), task.getOutputStream(), task.getR2R().getR2rFactory(), task.getR2S().getR2rFactory());
        return cqe;
    }
    public static class ContinuousProgramBuilder{
        private Task task;
        private Rule rule;


        public ContinuousProgramBuilder addTask(Task task){
            this.task = task;
            return this;
        }
        public ContinuousProgramBuilder addOptimizationRules(Rule rule){
            this.rule = rule;
            return this;
        }
        public ContinuousProgram build(){
            return new ContinuousProgram(this);
        }

    }
}
