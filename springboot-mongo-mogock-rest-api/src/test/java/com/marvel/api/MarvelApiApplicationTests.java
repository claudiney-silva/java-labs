package com.marvel.api;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import com.marvel.api.domain.CharacterDocument;
import com.marvel.api.repository.CharacterRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class MarvelApiApplicationTests {
 
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    {
        mongoDBContainer.start();
    }
 
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
 
    @Autowired
    private CharacterRepository characterRepository;
 
    @Test
    @DisplayName("Test Whether Characters are pre-populated")
    void shouldReturnPrePopulatedCharacters() {
        List<CharacterDocument> characters = characterRepository.findAll();
        assertTrue(characters.size() >= 10);
    }
 
    @Test
    @DisplayName("Should Find Character By Name")
    void shouldFindCharacterByName() {
        CharacterDocument expectedCharacter = CharacterDocument.builder().name("Test 1").build();
        characterRepository.save(expectedCharacter);
 
        CharacterDocument actualCharacter = characterRepository.findByName("Test 1").orElseThrow();
        assertEquals(expectedCharacter.getName(), actualCharacter.getName());
    }
}
