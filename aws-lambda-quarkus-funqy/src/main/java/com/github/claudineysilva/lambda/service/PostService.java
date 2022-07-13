package com.github.claudineysilva.lambda.service;

import com.github.claudineysilva.lambda.model.Post;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/posts")
@RegisterRestClient(configKey="api")
public interface PostService {
    @GET
    List<Post> findAll();

    @GET
    @Path("/{id}")
    Post findById(@PathParam("id") int id);
}
