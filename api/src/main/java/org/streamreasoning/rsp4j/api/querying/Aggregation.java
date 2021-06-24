package org.streamreasoning.rsp4j.api.querying;

import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.Objects;

public class Aggregation {
    private String tvg;
    private String inputVariable;
    private String outputVariable;
    private String functionName;

    public Aggregation(String tvg, String inputVariable, String outputVariable, String functionName) {
        this.tvg = tvg;
        this.inputVariable = RDFUtils.trimVar(inputVariable);
        this.outputVariable = RDFUtils.trimVar(outputVariable);
        this.functionName = functionName;
    }

    public String getTvg() {
        return tvg;
    }

    public String getInputVariable() {
        return inputVariable;
    }

    public String getOutputVariable() {
        return outputVariable;
    }

    public String getFunctionName() {
        return functionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aggregation that = (Aggregation) o;
        return Objects.equals(tvg, that.tvg) &&
               Objects.equals(inputVariable, that.inputVariable) &&
               Objects.equals(outputVariable, that.outputVariable) &&
               Objects.equals(functionName, that.functionName);
    }

    @Override
    public String toString() {
        return "Aggregation{" +
               "tvg='" + tvg + '\'' +
               ", inputVariable='" + inputVariable + '\'' +
               ", outputVariable='" + outputVariable + '\'' +
               ", functionName='" + functionName + '\'' +
               '}';
    }
}
