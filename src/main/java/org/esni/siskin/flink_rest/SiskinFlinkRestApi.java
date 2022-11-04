package org.esni.siskin.flink_rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.esni.siskin.flink_rest.bean.Jar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SiskinFlinkRestApi {

    private String flinkRestApiUrl;
    private String siskinVersion;
    private String libreSource;
    private CloseableHttpClient client;

    public SiskinFlinkRestApi(String flinkRestApiUrl, String siskinVersion, String libreSource) {

        if (!flinkRestApiUrl.endsWith("/"))
            flinkRestApiUrl = flinkRestApiUrl.substring(0, flinkRestApiUrl.length() - 1);

        this.flinkRestApiUrl = flinkRestApiUrl;
        this.siskinVersion = siskinVersion;
        this.libreSource = libreSource;
        this.client = HttpClients.createDefault();


    }

    public String getFlinkRestApiUrl() {
        return flinkRestApiUrl;
    }

    public void setFlinkRestApiUrl(String flinkRestApiUrl) {
        this.flinkRestApiUrl = flinkRestApiUrl;
    }

    private List<Jar> getJars() {

        ArrayList<Jar> jars = new ArrayList<>();
        try {
            CloseableHttpResponse res = client.execute(new HttpGet(this.flinkRestApiUrl + "jars"));
            if (res.getStatusLine().getStatusCode() != 200) return jars;

            JSONObject json = JSON.parseObject(EntityUtils.toString(res.getEntity()));

            for (Object files : json.getJSONArray("files")) {
                JSONObject node = JSON.parseObject(files.toString());
                jars.add(new Jar(node.getString("id"), node.getString("name"), node.getLong("uploaded")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return jars;
        }

        return jars;

    }

    private boolean uploadJar() {

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setContentType(ContentType.create("application/x-java-archive"));
        multipartEntityBuilder.addBinaryBody(String.format("siskin-core-%s-jar-with-dependencies.jar", siskinVersion), new File(libreSource + String.format("siskin-core-%s-jar-with-dependencies.jar", siskinVersion)));

        new HttpPost()

    }

    private boolean initSource() {

        List<String> jarNames = getJars().stream().map(Jar::getName).collect(Collectors.toList());

        if (jarNames.contains(String.format("siskin-core-%s-jar-with-dependencies.jar", siskinVersion))) {

        }

        return false;

    }

    public static void main(String[] args) {

        SiskinFlinkRestApi api = new SiskinFlinkRestApi("http://1.15.135.178:8181/", "1.0-SNAPSHOT", "");
        System.out.println(api.getJars());

    }

}
