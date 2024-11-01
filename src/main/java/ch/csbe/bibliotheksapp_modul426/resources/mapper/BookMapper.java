package ch.csbe.bibliotheksapp_modul426.resources.mapper;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.*;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper-Klasse für die Umwandlung von Book-Entitäten in DTOs und umgekehrt.
 * Verwendet MapStruct zur automatischen Mappung von Feldern.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    // Map Book Entity to BookShowDto
    BookShowDto toBookShowDto(Book book);

    // Map BookCreateDto to Book Entity
    Book toBookEntity(BookCreateDto bookCreateDto);

    // Aktualisiert eine bestehende Book-Entity mit Daten aus BookUpdateDto
    void updateBookEntity(BookUpdateDto bookUpdateDto, @MappingTarget Book book);

    // Map Book Entity to BookDetailDto, inklusive eingebetteter LoanDtos und Genre
    @Mapping(source = "loans", target = "loans")
    @Mapping(source = "genre", target = "genre")
    BookDetailDto toBookDetailDto(Book book);

    // Mapping von Loan zu LoanDto für eingebettete Loan-Daten in BookDetailDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "loanDate", target = "loanDate")
    @Mapping(source = "returnDate", target = "returnDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "user.id", target = "userId") // Referenz auf Benutzer-ID
    BookDetailDto.LoanDto toLoanDto(Loan loan);

    // Falls BookDetailDto eine Liste von LoanDto benötigt
    List<BookDetailDto.LoanDto> toLoanDtoList(List<Loan> loans);
}
