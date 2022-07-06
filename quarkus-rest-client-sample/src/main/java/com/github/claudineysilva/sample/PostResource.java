package com.github.claudineysilva.sample;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;

@Path("/blogs")
public class PostResource {

    @Inject
    @RestClient
    PostService postService;

    @GET
    public Set<Post> findAll() {
        return postService.findAll();
    }

    @GET
    @Path("/{id}")
    public Post findById(@PathParam("id") int id) {
        return postService.findById(id);
    }
}