package com.github.claudineysilva.lambda.funqy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.claudineysilva.lambda.model.Post;
import com.github.claudineysilva.lambda.service.PostService;
import com.github.claudineysilva.lambda.util.Util;
import io.quarkus.funqy.Funq;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

public class PostFunction {

    private static final Logger LOG = Logger.getLogger(PostFunction.class);

    @ConfigProperty(name = "greeting.message")
    String message;

    @Inject
    @RestClient
    PostService postService;

    @Funq
    public JsonNode proxy2(JsonNode event) {

        ObjectNode payload = Util.jsonMapper.createObjectNode();
        payload.set("streaming", event);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(event.get("awslogs").get("data").asText());

        try {
            LOG.info(decompress(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //LOG.info(payload);

        return payload;
    }


    public String decompress(byte[] bytes) throws Exception {
        final StringBuilder outStr = new StringBuilder();
        final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            outStr.append(line);
        }
        return outStr.toString();
    }

    @Funq
    public JsonNode proxy(JsonNode event) {

        ObjectNode payload = Util.jsonMapper.createObjectNode();
        payload.set("event", event);

        switch (event.get("detail-type").asText("default")) {
            case "Scheduled Event":
                payload.set("result", cloudWatch(event));
                break;

            case "default":
                payload.set("set", Util.jsonMapper
                        .createObjectNode()
                        .put("message", "processando o streaming")
                        .put("title", "titulo")
                );
                break;
        }

        LOG.info(payload);

        return payload.get("result");
    }

    public ObjectNode cloudWatch(JsonNode event) {
        ObjectNode payload = Util.jsonMapper.createObjectNode();

        payload.put("statusCode", 200);
        payload.set("body", Util.jsonMapper
                .createObjectNode()
                .put("message", "alarm cloud watch event")
                .put("title", message)
        );

        return payload;
    }


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