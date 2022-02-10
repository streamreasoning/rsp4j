package org.streamreasoning.rsp4j.operatorapi.monitoring;

public class Metric {

    private final long systemTime;
    private final String functionName;
    private final String componentName;
    private final String metricName;
    private final long metricResult;

    public Metric(String componentName, String functionName, String metricName, long metricResult, long systemTime){
        this.componentName = componentName;
        this.functionName = functionName;
        this.metricName = metricName;
        this.metricResult = metricResult;
        this.systemTime = systemTime;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getMetricName() {
        return metricName;
    }

    public long getMetricResult() {
        return metricResult;
    }

    @Override
    public String toString() {
        return String.format("Metric[component=%s, function=%s, metric=%s, resultTime=%dms @ %d]",componentName,functionName,metricName,metricResult,systemTime);
    }
}
