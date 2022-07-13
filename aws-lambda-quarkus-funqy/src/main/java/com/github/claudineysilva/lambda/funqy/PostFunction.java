package com.github.claudineysilva.lambda.funqy;

import com.github.claudineysilva.lambda.model.Post;
import com.github.claudineysilva.lambda.service.PostService;
import io.quarkus.funqy.Funq;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PostFunction {

    private static final Logger LOG = Logger.getLogger(PostFunction.class);

    @Inject
    @RestClient
    PostService postService;

    @Funq("all")
    public List<Post> findAll() {
     return postService.findAll();
    }

    @Funq
    public Post find(Post post) {
        Post myPost = postService.findById(Integer.parseInt(post.id));
        LOG.info(post);
        return myPost;
    }

    @Funq
    public String greets(String name) {
        return "Hello "+name;
    }

    @Funq
    public Post fake(Post post) {
        post.id = UUID.randomUUID().toString();
        return post;
    }

    @Funq
    public HashMap<String, Object> hash(HashMap<String, Object> map) {
        map.put("result", "success");
        LOG.info(map);
        return map;
    }

}