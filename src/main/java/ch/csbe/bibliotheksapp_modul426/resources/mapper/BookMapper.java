package ch.csbe.bibliotheksapp_modul426.resources.mapper;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.*;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper-Klasse für die Umwandlung von Book-Entitäten in DTOs und umgekehrt.
 * Verwendet MapStruct zur automatischen Mappung von Feldern.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    // Map Book Entity to BookShowDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "available", target = "available")
    BookShowDto toBookShowDto(Book book);

    // Map BookCreateDto to Book Entity
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    Book toBookEntity(BookCreateDto bookCreateDto);

    // Aktualisiert eine bestehende Book-Entity mit Daten aus BookUpdateDto
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "available", target = "available")
    void updateBookEntity(BookUpdateDto bookUpdateDto, @MappingTarget Book book);
}
