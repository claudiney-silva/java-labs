package com.github.claudineysilva.lambda;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;

@Path("/extensions")
@RegisterRestClient(configKey="extensions-api")
public interface ExtensionsService {

    @GET
    Extension getById(@QueryParam Integer id);

}