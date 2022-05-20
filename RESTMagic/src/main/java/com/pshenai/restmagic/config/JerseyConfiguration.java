package com.pshenai.restmagic.config;

import com.pshenai.restmagic.MagicResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/jersey")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(MagicResource.class);
    }
}
