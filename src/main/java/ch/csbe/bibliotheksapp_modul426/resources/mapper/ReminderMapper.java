package ch.csbe.bibliotheksapp_modul426.resources.mapper;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.*;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Reminder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper-Klasse für die Umwandlung von Reminder-Entitäten in DTOs und umgekehrt.
 * Verwendet MapStruct zur automatischen Mappung von Feldern.
 */
@Mapper(componentModel = "spring")
public interface ReminderMapper {

    // Map Reminder Entity to ReminderShowDto
    @Mapping(source = "loan.id", target = "loanId")
    @Mapping(source = "emailSent", target = "emailSent")
    ReminderShowDto toReminderDto(Reminder reminder);

    // Map ReminderCreateDto to Reminder Entity
    @Mapping(source = "loanId", target = "loan.id")
    Reminder toReminderEntity(ReminderCreateDto reminderCreateDto);

    // Aktualisiere eine bestehende Reminder-Entity mit Daten aus ReminderUpdateDto
    @Mapping(source = "emailSent", target = "emailSent")
    void updateReminderEntity(ReminderUpdateDto reminderUpdateDto, @MappingTarget Reminder reminder);
}
