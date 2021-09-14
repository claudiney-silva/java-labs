package br.com.effetivo.character.api.mapper;

import br.com.effetivo.character.api.domain.CharacterDocument;
import br.com.effetivo.character.api.response.Character;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CharacterMapper {
    public static final CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);
    public abstract Character toCharacter(CharacterDocument characterDocument);
}
