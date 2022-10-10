import jakarta.annotation.Resource;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.enigma.app.SecretResource;
import org.enigma.domain.secret.Secret;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import java.net.URI;

import static junit.framework.TestCase.assertEquals;

public class test extends JerseyTest {
    @Override
    protected Application configure(){
        return new ResourceConfig(SecretResource.class);
    }
    /*@Override
    protected URI getBaseUri() {
        return URI.create("http://localhost:8080/");
    }*/
    @Test
    public void testForAnswerFromServer(){
        Response response = target("api/secret").queryParam("uuid","6e458a34-bce8-4770-ba56-026267a9deff").request().get();
        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.TEXT_HTML, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        Secret content = response.readEntity(Secret.class);
        assertEquals("Content of ressponse is: ", "{\"encryptedSecret\":\"U2FsdGVkX1+K4g7EHyMp6k/+sR5gcSAZKiZXi/o21ms=\",\"password\":\"\",\"needPassword\":false,\"timeToBurn\":\"\",\"numberOfReads\":\"1\",\"showAndBurn\":false}", content);
    }

    @Test
    public void testForSendJson(){
        Response response = target("api/secret").request().post(Entity.json("{\"uuid\":\"\",\"pssword\":\"\",\"needPassword\":false,\"timeToBurn\":\"\",\"numberOfReads\":\"5\",\"showAndBurn\":false}"));

       assertEquals("Http Response should be 400", Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
}
