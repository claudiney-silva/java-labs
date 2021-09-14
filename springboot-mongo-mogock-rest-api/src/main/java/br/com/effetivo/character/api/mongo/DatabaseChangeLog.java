package br.com.effetivo.character.api.mongo;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

import br.com.effetivo.character.api.domain.CharacterDocument;
import br.com.effetivo.character.api.repository.CharacterRepository;
 
import java.util.ArrayList;
import java.util.List;
 
@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "0001", id = "populateDatabase", author = "Claudiney")
    public void populateDatabase(CharacterRepository characterRepository) {
        List<CharacterDocument> list = new ArrayList<>();
        list.add(CharacterDocument.builder().name("Character 1").build());
        list.add(CharacterDocument.builder().name("Character 2").build());
        list.add(CharacterDocument.builder().name("Character 3").build());
        list.add(CharacterDocument.builder().name("Character 4").build());
        list.add(CharacterDocument.builder().name("Character 5").build());
        list.add(CharacterDocument.builder().name("Character 6").build());
        list.add(CharacterDocument.builder().name("Character 7").build());
        list.add(CharacterDocument.builder().name("Character 8").build());
        list.add(CharacterDocument.builder().name("Character 9").build());
        list.add(CharacterDocument.builder().name("Character 10").build());                
         
        characterRepository.insert(list);
    }

}