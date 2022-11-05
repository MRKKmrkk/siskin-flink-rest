package org.esni.flink.rest.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.esni.flink.rest.api.bean.Jar;
import org.esni.flink.rest.api.bean.Job;
import org.esni.flink.rest.api.bean.State;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FlinkRestAPI implements Closeable {

    private String flinkRestUrl;
    private CloseableHttpClient client;

    private FlinkRestAPI(String flinkRestUrl) {

        if (flinkRestUrl.endsWith("/")) flinkRestUrl = flinkRestUrl.substring(0, flinkRestUrl.length() - 1);

        this.flinkRestUrl = flinkRestUrl;
        this.client = HttpClients.createDefault();

    }

    private boolean checkStatusCode(CloseableHttpResponse res, int code) {

        return res.getStatusLine().getStatusCode() == code;

    }

    public void close() throws IOException {

        client.close();

    }

    private void closeRes(CloseableHttpResponse res) {

        if (res != null) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * /jars
     * Verb: GET	Response code: 200 OK
     * Returns a list of all jars previously uploaded via '/jars/upload'.
     *
     * return a empty list when no jar find out.
     */
    public List<Jar> getJars() {

        ArrayList<Jar> jars = new ArrayList<>();
        CloseableHttpResponse res = null;

        JSONObject json = null;
        try {
             res = client.execute(new HttpGet(flinkRestUrl + "/jars"));

            if (!checkStatusCode(res, 200)) return jars;

            json = JSON.parseObject(EntityUtils.toString(res.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
            return jars;
        }
        finally {
            closeRes(res);
        }

        for (Object file : json.getJSONArray("files")) {
            JSONObject node = JSON.parseObject(file.toString());
            jars.add(new Jar(node.getString("id"), node.getString("name"), node.getLong("uploaded")));
        }

        return jars;

    }

    /**
     * /jars/:jarid
     * Verb: DELETE	Response code: 200 OK
     * Deletes a jar previously uploaded via '/jars/upload'.
     * Path parameters
     * jarid - String value that identifies a jar. When uploading the jar a path is returned, where the filename is the ID.
     * This value is equivalent to the `id` field in the list of uploaded jars (/jars).
     */

    public boolean deleteJar(String jarId) {

        CloseableHttpResponse res = null;
        try {
            res = client.execute(new HttpDelete(flinkRestUrl + "/jars/" + jarId));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
           closeRes(res);
        }

        return checkStatusCode(res, 200);

    }

    /**
     * /jars/:jarid/run
     * Verb: POST	Response code: 200 OK
     * Submits a job by running a jar previously uploaded via '/jars/upload'. Program arguments can be passed both via the JSON request (recommended) or query parameters.
     * Path parameters
     * jarid - String value that identifies a jar. When uploading the jar a path is returned, where the filename is the ID. This value is equivalent to the `id` field in the list of uploaded jars (/jars).
     * Query parameters
     * allowNonRestoredState (optional): Boolean value that specifies whether the job submission should be rejected if the savepoint contains state that cannot be mapped back to the job.
     * savepointPath (optional): String value that specifies the path of the savepoint to restore the job from.
     * program-args (optional): Deprecated, please use 'programArg' instead. String value that specifies the arguments for the program or plan
     * programArg (optional): Comma-separated list of program arguments.
     * entry-class (optional): String value that specifies the fully qualified name of the entry point class. Overrides the class defined in the jar file manifest.
     * parallelism (optional): Positive integer value that specifies the desired parallelism for the job.
     *
     * return job id when run jar success
     * return null when run jar fail
     */

    public Job runJar(String jarId, String entryClass, int parallelism, String programArgs, boolean allowNonRestoredState, String savepointPath) {


        CloseableHttpResponse res = null;
        try {
            HttpPost post = new HttpPost(flinkRestUrl + "/jars/" + jarId + "/run");
            post.addHeader("Content-type","application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");

            JSONObject json = new JSONObject();
            json.put("entryClass", entryClass);
            json.put("allowNonRestoredState", allowNonRestoredState);
            json.put("parallelism", parallelism);
            json.put("programArgs", programArgs);
            json.put("savepointPath", savepointPath);

            post.setEntity(new StringEntity(json.toJSONString(), StandardCharsets.UTF_8));

            res = client.execute(post);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (!checkStatusCode(res, 200)) {

            // todo: need match error in here
            return null;

        }

        Job job = new Job();
        try {
            String jobId = JSONObject.parseObject(EntityUtils.toString(res.getEntity())).getString("jobid");
            job.setJid(jobId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            closeRes(res);
        }
        return job;

    }

    public Job runJar(String jarId, String entryClass, int parallelism, String programArgs, boolean allowNonRestoredState) {

        return runJar(jarId, entryClass, parallelism, programArgs, allowNonRestoredState, null);

    }

    public Job runJar(String jarId, String entryClass, int parallelism, String programArgs) {

        return runJar(jarId, entryClass, parallelism, programArgs, false, null);

    }

    public Job runJar(String jarId, String entryClass, int parallelism) {

        return runJar(jarId, entryClass, parallelism, null, false, null);

    }

    /**
     * /jobs
     * Verb: GET	Response code: 200 OK
     * Returns an overview over all jobs and their current state.
     */

        public List<Job> getJobs()  {

        ArrayList<Job> jobs = new ArrayList<>();

            JSONObject json = null;
            CloseableHttpResponse res = null;
            try {
                res = client.execute(new HttpGet(flinkRestUrl + "/jobs"));

                if (res.getStatusLine().getStatusCode() != 200) return jobs;
                json = JSON.parseObject(EntityUtils.toString(res.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
                return jobs;
            }
            finally {
                closeRes(res);
            }

            for (Object o : json.getJSONArray("jobs")) {
            JSONObject node = JSON.parseObject(o.toString());
            Job job = new Job();
            job.setJid(node.getString("id"));
            job.setState(State.of(node.getString("status")));

            jobs.add(job);
        }

        return jobs;

    }

    /**
     * /jobs/:jobid
     * Verb: GET	Response code: 200 OK
     * Returns details of a job.
     * Path parameters
     * jobid - 32-character hexadecimal string value that identifies a job.
     */
    public Job getJob(String jobId) {

        JSONObject json = null;
        CloseableHttpResponse res = null;

        try {
            res = client.execute(new HttpGet(flinkRestUrl + "/jobs/" + jobId));
            json = JSON.parseObject(EntityUtils.toString(res.getEntity()));

            if (!checkStatusCode(res, 200)) return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            closeRes(res);
        }

        return new Job(
                json.getString("jid"),
                json.getString("name"),
                json.getBoolean("isStoppable"),
                State.of(json.getString("state")),
                json.getLong("start-time"),
                json.getLong("end-time"),
                json.getLong("duration"),
                json.getLong("now"),
                json.getString("timestamps"),
                json.getString("vertices"),
                json.getString("status-counts"),
                json.getString("plan")
                );

    }

    /**
     * /jobs/:jobid
     * Verb: PATCH	Response code: 202 Accepted
     * Terminates a job.
     * Path parameters
     * jobid - 32-character hexadecimal string value that identifies a job.
     * Query parameters
     * mode (optional): String value that specifies the termination mode. The only supported value is: "cancel".
     */
    public boolean terminateJob(String jobId) {

        HttpPatch httpPatch = new HttpPatch(flinkRestUrl + "/jobs/" + jobId);
        CloseableHttpResponse res = null;
        try {
            res = client.execute(httpPatch);

            if (!checkStatusCode(res, 202)) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeRes(res);
        }

        return true;

    }

    /**
     *
     * /jars/upload
     * Verb: POST	Response code: 200 OK
     * Uploads a jar to the cluster. The jar must be sent as multi-part data.
     * Make sure that the "Content-Type" header is set to "application/x-java-archive", as some http libraries do not add the header by default.
     * Using 'curl' you can upload a jar via 'curl -X POST -H "Expect:" -F "jarfile=@path/to/flink-job.jar" http://hostname:port/jars/upload'.
     *
     * return a job when upload success
     * return null when upload fail
     */
    public Jar uploadJar(String path) {

        File file = new File(path);

        JSONObject json = null;
        CloseableHttpResponse res = null;

        try {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addBinaryBody(file.getName(), new FileInputStream(file), ContentType.create("application/x-java-archive", StandardCharsets.UTF_8), file.getName());

            HttpPost post = new HttpPost(flinkRestUrl + "/jars/upload");
            post.setEntity(entityBuilder.build());
            res = client.execute(post);

            if (!checkStatusCode(res, 200)) return null;

            json = JSON.parseObject(EntityUtils.toString(res.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            closeRes(res);
        }

        if (!"success".equals(json.getString("status"))) {
           return null;
        }

        Jar jar = new Jar();
        String[] fields = json.getString("filename").split("/");
        jar.setName(fields[fields.length - 1]);
        return jar;

    }

    public static FlinkRestAPI create(String flinkRestUrl) {

        return new FlinkRestAPI(flinkRestUrl);

    }

    public static void main(String[] args) throws IOException {

//        Properties properties = new Properties();
//        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("flink.properties");
//        properties.load(resourceAsStream);
//
//        FlinkRestAPI api = create(properties.getProperty("url"));

        FlinkRestAPI api = FlinkRestAPI.create("http://1.15.135.178:8181");
        List<Jar> jars = api.getJars();

        System.out.println(api.runJar(jars.get(1).getId(), "org.esni.siskin_core.app.ETLApplication", 1));

    }


}
