package com.github.claudineysilva.sample;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Set;

@Path("/posts")
@RegisterRestClient(configKey="api")
public interface PostService {
    @GET
    Set<Post> findAll();

    @GET
    @Path("/{id}")
    Post findById(@PathParam("id") int id);
}