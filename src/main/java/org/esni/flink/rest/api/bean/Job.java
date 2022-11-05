package org.esni.flink.rest.api.bean;

public class Job {

    private String jid;
    private String name;
    private Boolean isStoppable;
    private State state;
    private Long startTime;
    private Long endTime;
    private Long duration;
    private Long now;

    private String timestamps;
    private String vertices;
    private String statusCounts;
    private String plan;

    public Job() {

    }

    public Job(String jid, String name, Boolean isStoppable, State state, Long startTime, Long endTime, Long duration, Long now, String timestamps, String vertices, String statusCounts, String plan) {
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

    public Boolean isStoppable() {
        return isStoppable;
    }

    public void setStoppable(Boolean stoppable) {
        isStoppable = stoppable;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
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
