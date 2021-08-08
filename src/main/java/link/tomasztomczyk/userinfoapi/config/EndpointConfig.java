package link.tomasztomczyk.userinfoapi.config;

import link.tomasztomczyk.userinfoapi.rest.UserApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class EndpointConfig extends ResourceConfig {
    public EndpointConfig() {
        register(UserApi.class);
    }
}
