package ch.csbe.bibliotheksapp_modul426.resources.mapper;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.*;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper-Klasse für die Umwandlung von Loan-Entitäten in DTOs und umgekehrt.
 * Verwendet MapStruct zur automatischen Mappung von Feldern.
 */
@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    LoanShowDto toLoanShowDto(Loan loan);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "bookId", target = "book.id")
    Loan toLoanEntity(LoanCreateDto loanCreateDto);

    // Aktualisiert eine bestehende Loan-Entity mit Daten aus LoanUpdateDto
    @Mapping(source = "status", target = "status")
    @Mapping(source = "returnDate", target = "returnDate")
    void updateLoanEntity(LoanUpdateDto loanUpdateDto, @MappingTarget Loan loan);
}



