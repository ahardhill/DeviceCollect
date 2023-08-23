import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;


public class PostPhoneInfo {

    public static String Host = "http://localhost:8080";

    public static String url = "/api/device/insert";

    public static SortedMap<String, Object> doPost(String sendMsgParamsVoStr) throws JsonProcessingException {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy) (x509Certificates, s) -> true).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).
                setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

        HttpPost httpPost = new HttpPost(Host+url);

        httpPost.setHeader("Content-Type", "application/json");

        if (sendMsgParamsVoStr != null) {
            StringEntity entity = new StringEntity(sendMsgParamsVoStr, ContentType.APPLICATION_JSON);
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
        }

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
            return parseHttpStatus(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }

    private static SortedMap<String, Object> parseHttpStatus(CloseableHttpResponse httpRsp) throws JsonProcessingException {
        StatusLine status = httpRsp.getStatusLine();
        SortedMap<String, Object> responseObject = new TreeMap<>();
        responseObject.put("status", status.getStatusCode());

        if (status.getStatusCode() == 200) {
            responseObject.put("result", readHttpResponse(httpRsp));
        } else {
            responseObject.put("error", readHttpResponse(httpRsp));
        }


        return responseObject;
    }

    private static String readHttpResponse(CloseableHttpResponse httpRsp) {
        HttpEntity rspEntity = httpRsp.getEntity();
        InputStream in = null;
        try {
            in = rspEntity.getContent();
            byte[] buffer = readInputStream(in);
            return new String(buffer, "UTF-8");

        } catch (IOException e) {
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] readInputStream(InputStream in) throws IOException {
        byte[] content;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int length = in.available();
            if (length == 0) {
                length = 1024;
            }
            byte[] buffer = new byte[length];
            int readLength;
            while ((readLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, readLength);
            }
            content = out.toByteArray();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}
