package link.tomasztomczyk.userinfoapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Configuration
public class TargetApiConfig {
    final Logger logger = LoggerFactory.getLogger(TargetApiConfig.class);
    final WebTarget target;

    public TargetApiConfig(
            @Value("${service.target}") String target,
            @Value("${service.resource}") String resource) {
                if (isBlank(target) || isBlank(resource)) {
                    final var msg = "Neither target nor resource variables can be empty";
                    logger.error(msg);
                    throw new IllegalStateException(msg);
                }
                this.target = ClientBuilder.newClient()
                                .target(target)
                                .path(resource);
            }
    @Bean
    public WebTarget getTarget() {
        return target;
    }

    public boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
