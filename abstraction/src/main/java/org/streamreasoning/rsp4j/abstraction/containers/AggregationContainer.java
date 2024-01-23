package org.streamreasoning.rsp4j.abstraction.containers;

public  class AggregationContainer<R> {
        private String tvgName;
        private String functionName;
        private String inputVariable;
        private String outputVariable;

    public AggregationContainer() {
    }

    public AggregationContainer(String tvgName, String functionName, String inputVariable, String outputVariable) {
        this.tvgName = tvgName;
        this.functionName = functionName;
        this.inputVariable = inputVariable;
        this.outputVariable = outputVariable;
    }

    public String getTvgName() {
        return this.tvgName;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public String getInputVariable() {
        return this.inputVariable;
    }

    public String getOutputVariable() {
        return this.outputVariable;
    }
}

