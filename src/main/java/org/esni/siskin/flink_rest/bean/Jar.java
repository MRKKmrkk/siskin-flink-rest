package org.esni.siskin.flink_rest.bean;

public class Jar {

    private String id;
    private String name;
    private long uploaded;

    public Jar() {

    }

    public Jar(String id, String name, long uploaded) {
        this.id = id;
        this.name = name;
        this.uploaded = uploaded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUploaded() {
        return uploaded;
    }

    public void setUploaded(long uploaded) {
        this.uploaded = uploaded;
    }

    @Override
    public String toString() {
        return "Jar{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uploaded=" + uploaded +
                '}';
    }

}
