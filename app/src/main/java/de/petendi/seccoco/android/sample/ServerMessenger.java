package de.petendi.seccoco.android.sample;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import de.petendi.seccoco.android.Seccoco;
import de.petendi.seccoco.android.model.EncryptedMessage;
import de.petendi.seccoco.android.model.Identity;

public class ServerMessenger {

    private final Seccoco seccoco;
    private final String server;
    private final String certificate;

    public ServerMessenger(Seccoco seccoco, String server, String certificate) {
        this.seccoco = seccoco;
        this.server = server;
        this.certificate = certificate;
    }


    public String sendMessage(String message) throws IOException {

        String responseString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Identity identity = seccoco.identities().extractFromPem(new StringReader(certificate));
        EncryptedMessage encryptedMessage = seccoco.crypto().encrypt(message.getBytes(), identity);
        String serialized = objectMapper.writeValueAsString(encryptedMessage);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(server + "/" + seccoco.identities().getOwnIdentity().getFingerPrint());
        StringEntity stringEntity = new StringEntity(serialized);
        stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                HttpEntity responseEntity = httpResponse.getEntity();
                EncryptedMessage responseMessage = objectMapper.readValue(responseEntity.getContent(), EncryptedMessage.class);
                byte[] plainResponse = seccoco.crypto().decrypt(responseMessage);
                responseString = new String(plainResponse);
                EntityUtils.consume(responseEntity);
            } finally {
                httpResponse.close();
            }
        } else {
            throw new IllegalArgumentException("server response was not OK: " + httpResponse.getStatusLine());
        }
        return responseString;
    }


}
