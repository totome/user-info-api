package link.tomasztomczyk.userinfoapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("users")
public class UserApi {
    public static final String LOGIN = "userLogin";
    public static final String LOGIN_PATH = "{"+LOGIN+"}";
    public final WebTarget target;

    @Autowired
    public UserApi(WebTarget target) {
        this.target = target;
    }

    @GET
    @Path(LOGIN_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam(LOGIN) String login) {
        var resp = target.path(login)
                .request()
                .get();
        return Response.fromResponse(resp).build();
    }
}
