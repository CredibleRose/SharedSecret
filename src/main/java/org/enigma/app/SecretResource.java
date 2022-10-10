package org.enigma.app;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.enigma.domain.secret.SecretDataSource;
import org.enigma.domain.secret.Secret;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("secret")
public class SecretResource {
    private final SecretDataSource secretDataSource;
    @Inject
    public SecretResource(SecretDataSource secretDataSource) {
        this.secretDataSource = secretDataSource;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSecretById(@QueryParam("uuid") String uuid, String password) {
        Map<String, String> uuidAndPass = new HashMap<String, String>();
        uuidAndPass.put(uuid, password);
        return Response.status(200).entity(secretDataSource.getSecretById(uuidAndPass)).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putSecret(Secret secret) {
        String enteredSecret = secret.getEncryptedSecret();
        String enteredPassword = secret.getPassword();
        if (enteredSecret == null || enteredSecret.trim().length() == 0) {
            return Response.status(400).entity("Enter text in the message field!").build();
        }
        if (secret.getNeedPassword()) {
            if (enteredPassword == null || enteredPassword.trim().length() == 0) {
                return Response.status(400).entity("Enter password in the password field!").build();
            }
        }
        secretDataSource.putSecret(secret);
        return Response.status(200).entity(secret.getUuid()).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/auth")
    public Response getSecretByIdAndPass(@QueryParam("uuid") String uuid, @Context HttpHeaders headers)
    {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        String password = authHeaders.get(0);
        return getSecretById(uuid, password);
    }
}