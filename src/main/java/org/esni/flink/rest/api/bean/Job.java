package org.esni.flink.rest.api.bean;

public class Job {

    private String jid;
    private String name;
    private boolean isStoppable;
    private State state;
    private long startTime;
    private long endTime;
    private long duration;
    private long now;

    private String timestamps;
    private String vertices;
    private String statusCounts;
    private String plan;

    public Job() {

    }

    public Job(String jid, String name, boolean isStoppable, State state, long startTime, long endTime, long duration, long now, String timestamps, String vertices, String statusCounts, String plan) {
        this.jid = jid;
        this.name = name;
        this.isStoppable = isStoppable;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.now = now;
        this.timestamps = timestamps;
        this.vertices = vertices;
        this.statusCounts = statusCounts;
        this.plan = plan;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStoppable() {
        return isStoppable;
    }

    public void setStoppable(boolean stoppable) {
        isStoppable = stoppable;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public String getVertices() {
        return vertices;
    }

    public void setVertices(String vertices) {
        this.vertices = vertices;
    }

    public String getStatusCounts() {
        return statusCounts;
    }

    public void setStatusCounts(String statusCounts) {
        this.statusCounts = statusCounts;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jid='" + jid + '\'' +
                ", name='" + name + '\'' +
                ", isStoppable=" + isStoppable +
                ", state=" + state +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", now=" + now +
                ", timestamps='" + timestamps + '\'' +
                ", vertices='" + vertices + '\'' +
                ", statusCounts='" + statusCounts + '\'' +
                ", plan='" + plan + '\'' +
                '}';
    }
}
