package link.tomasztomczyk.userinfoapi.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadGatewayExceptionMapper implements ExceptionMapper<UserApi.BadGatewayException> {
    @Override
    public Response toResponse(UserApi.BadGatewayException ex) {
        return Response.status(502).entity(ex.getMessage()).type("text/plain")
                .build();
    }
}
