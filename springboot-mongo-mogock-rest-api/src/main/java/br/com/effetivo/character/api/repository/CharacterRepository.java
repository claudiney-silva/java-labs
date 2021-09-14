package br.com.effetivo.character.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.effetivo.character.api.domain.CharacterDocument;

public interface CharacterRepository extends MongoRepository<CharacterDocument, String> {
    @Query("{'name': ?0}")
    Optional<CharacterDocument> findByName(String name);   
}
