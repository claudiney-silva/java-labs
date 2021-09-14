package br.com.effetivo.character.api.controller;

import lombok.RequiredArgsConstructor;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import br.com.effetivo.character.api.domain.CharacterDocument;
import br.com.effetivo.character.api.mapper.CharacterMapper;
import br.com.effetivo.character.api.service.CharacterService;
import br.com.effetivo.character.api.response.Character;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    
    private final CharacterService characterService;
    
    @GetMapping("")
    public ResponseEntity<Page<CharacterDocument>> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(characterService.findAll(pageable));
    } 
    
    @GetMapping("/{id}")
    @Operation(summary = "Find a character by id", description="Fetches lists of comic characters with optional filters. See notes on individual parameters below.", tags ={"characters"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "404", description = "Character not found."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<Character> findById(@PathVariable String id) {
        CharacterDocument characterDocument = characterService.findByIdOrThrowBadRequestException(id);
        Character character = CharacterMapper.INSTANCE.toCharacter(characterDocument);
        return ResponseEntity.ok(character);
    }    
    
    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<CharacterDocument> findByName(@PathVariable String name) {
        return ResponseEntity.ok(characterService.findByName(name));
    }    

    @PostMapping
    public ResponseEntity<CharacterDocument> save(@RequestBody CharacterDocument character) {
        return ResponseEntity.ok(characterService.save(character));
    }  
    
    @PutMapping
    public ResponseEntity<CharacterDocument> update(@RequestBody CharacterDocument character) {
        characterService.update(character);
        return ResponseEntity.status(HttpStatus.OK).build();
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }    
}

