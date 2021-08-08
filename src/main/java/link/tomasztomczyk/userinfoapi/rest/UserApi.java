package link.tomasztomczyk.userinfoapi.rest;

import link.tomasztomczyk.userinfoapi.config.TargetApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.familyOf;

@Component
@Path("users")
public class UserApi {
    private static final String LOGIN = "userLogin";
    private static final String LOGIN_CAPTOR = "{"+LOGIN+"}";
    private static final int OK = Response.Status.OK.getStatusCode();
    private final Logger logger = LoggerFactory.getLogger(UserApi.class);
    private final WebTarget target;

    @Autowired
    public UserApi(WebTarget target) {
        this.target = target;
    }

    @GET
    @Path(LOGIN_CAPTOR)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam(LOGIN) String login) {
        final var resp = getResponse(login);
        final int status = resp.getStatus();
        final Response result;

        if (OK == status) {
            result = readResponse(resp);
        } else if (CLIENT_ERROR == familyOf(status)) {
            result = Response.fromResponse(resp).build();
        } else {
            result = Response.fromResponse(resp).status(Response.Status.BAD_GATEWAY).build();
        }

        return result;
    }

    private Response getResponse(String login) {
        return target.path(login)
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    private Response readResponse(Response resp) {
        final Response result;
        SourceUserData gud = null;

        try {
            gud = resp.readEntity(SourceUserData.class);
        } catch (ProcessingException ex) {
            ex.printStackTrace();
        }

        result = Response.ok(gud).build();
        return result;
    }
}
