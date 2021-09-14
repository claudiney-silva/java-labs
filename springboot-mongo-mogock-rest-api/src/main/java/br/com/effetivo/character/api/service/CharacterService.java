package br.com.effetivo.character.api.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.effetivo.character.api.domain.CharacterDocument;
import br.com.effetivo.character.api.repository.CharacterRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterDocument findByName(String name) {
        return characterRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot Find Character by Name - %s", name)));
    }    

    public Page<CharacterDocument> findAll(Pageable pageable) {
        return characterRepository.findAll(pageable);
    }

    public CharacterDocument findByIdOrThrowBadRequestException(String id) {
        return characterRepository.findById(id).orElseThrow(() -> new
            ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));
            //return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

    }    

    public CharacterDocument save(CharacterDocument character) {
        return characterRepository.insert(character);
    }

    public void update(CharacterDocument character) {
        characterRepository.findById(character.getId()).orElseThrow(() -> new RuntimeException(String.format("Cannot Find Character by ID %s", character.getId())));
        characterRepository.save(character);
    }  
    
    public void delete(String id) {
        characterRepository.deleteById(id);
    }    
}


