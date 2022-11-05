package org.esni.flink.rest.api.bean;

public class State {

    private String stateName;

    private State(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return stateName;
    }

    public static State of(String state) {
        return new State(state);
    }

    public static State CREATED = of("CREATED");
    public static State FAILED = of("FAILED");
    public static State RESTARTING = of("RESTARTING");
    public static State FAILING = of("FAILING");
    public static State CANCELED = of("CANCELED");
    public static State FINISHED = of("FINISHED");
    public static State RECONCILING = of("RECONCILING");
    public static State RUNNING = of("RUNNING");
    public static State CANCELLING = of("CANCELLING");
    public static State SUSPENDED = of("SUSPENDED");

}
