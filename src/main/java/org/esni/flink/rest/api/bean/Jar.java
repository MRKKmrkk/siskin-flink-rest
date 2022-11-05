package org.esni.flink.rest.api.bean;

public class Jar {

    private String id;
    private String name;
    private Long uploaded;

    public Jar(String id, String name, Long uploaded) {
        this.id = id;
        this.name = name;
        this.uploaded = uploaded;
    }


    public Jar() {

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

    public Long getUploaded() {
        return uploaded;
    }

    public void setUploaded(Long uploaded) {
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
