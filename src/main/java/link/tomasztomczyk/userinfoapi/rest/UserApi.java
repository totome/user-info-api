package link.tomasztomczyk.userinfoapi.rest;

import link.tomasztomczyk.userinfoapi.model.InputUserData;
import link.tomasztomczyk.userinfoapi.model.OutputUserData;
import link.tomasztomczyk.userinfoapi.model.UserDataFactory;
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
    private final WebTarget target;
    private final UserDataFactory dataFactory;

    @Autowired
    public UserApi(WebTarget target, UserDataFactory dataFactory) {
        this.target = target;
        this.dataFactory = dataFactory;
    }

    @GET
    @Path(LOGIN_CAPTOR)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam(LOGIN) String login) {
        final var resp = getResponse(login);
        final int status = resp.getStatus();
        final Response result;
        if (OK == status) {
            var incommingData = readResponse(resp);
            var outgoingData = translateData(incommingData);
            result = Response.ok(outgoingData).build();
        } else if (CLIENT_ERROR == familyOf(status)) {
            result = Response.fromResponse(resp).build();
        } else {
            throw new BadGatewayException("Downstream proxy unexpected error");
        }
        return result;
    }

    private Response getResponse(String login) {
        return target.path(login)
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    private InputUserData readResponse(Response resp) {
        try {
            return resp.readEntity(GithubUserDTO.class)
                    .getData();
        } catch (ProcessingException ex) {
            throw new BadGatewayException(ex.getMessage());
        }
    }

    private OutputUserData translateData(InputUserData input) {
        try {
            return dataFactory.create(input);
        } catch (UserDataFactory.UserDataInvalidException ex) {
            throw new BadGatewayException(ex.getMessage());
        }
    }

    public static final class BadGatewayException extends RuntimeException{
        public BadGatewayException(String msg) {
            super(msg);
        }
    }
}
