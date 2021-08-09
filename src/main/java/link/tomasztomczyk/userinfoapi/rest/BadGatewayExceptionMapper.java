package link.tomasztomczyk.userinfoapi.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadGatewayExceptionMapper implements ExceptionMapper<UserApi.BadGatewayException> {
    private final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Override
    public Response toResponse(UserApi.BadGatewayException ex) {
        logger.error(ex.getMessage());
        return Response.status(502).entity(ex.getMessage()).type("text/plain")
                .build();
    }
}
