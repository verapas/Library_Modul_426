package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    /**
     * Gibt eine Liste aller Erinnerungen zurück.
     * Methode: GET
     * URL: http://localhost:8080/reminders
     */
    @GetMapping
    @Operation(summary = "Gibt eine Liste aller Erinnerungen zurück.")
    @ApiResponse(responseCode = "200", description = "Liste aller Erinnerungen erfolgreich zurückgegeben.")
    public ResponseEntity<List<ReminderShowDto>> getAllReminders() {
        List<ReminderShowDto> reminders = reminderService.findAll();
        return ResponseEntity.ok(reminders);
    }

    /**
     * Gibt die Details einer Erinnerung anhand der ID zurück.
     * Methode: GET
     * URL: http://localhost:8080/reminders/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Gibt die Details einer bestimmten Erinnerung zurück.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Erinnerungsdetails erfolgreich zurückgegeben."),
            @ApiResponse(responseCode = "404", description = "Erinnerung nicht gefunden.")
    })
    public ResponseEntity<ReminderShowDto> getReminderById(
            @Parameter(description = "ID der Erinnerung, die abgerufen werden soll.", example = "1")
            @PathVariable Integer id) {
        ReminderShowDto reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reminder);
    }

    /**
     * Erstellt eine neue Erinnerung.
     * Methode: POST
     * URL: http://localhost:8080/reminders
     */
    @PostMapping
    @Operation(summary = "Erstellt eine neue Erinnerung.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Erinnerung erfolgreich erstellt."),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten.")
    })
    public ResponseEntity<ReminderShowDto> createReminder(
            @Parameter(description = "Details der neuen Erinnerung.")
            @RequestBody ReminderCreateDto reminderCreateDto) {
        ReminderShowDto createdReminder = reminderService.save(reminderCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReminder);
    }

    /**
     * Aktualisiert die Informationen einer bestehenden Erinnerung.
     * Methode: PUT
     * URL: http://localhost:8080/reminders/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Aktualisiert die Informationen einer bestehenden Erinnerung.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Erinnerung erfolgreich aktualisiert."),
            @ApiResponse(responseCode = "404", description = "Erinnerung nicht gefunden.")
    })
    public ResponseEntity<ReminderShowDto> updateReminder(
            @Parameter(description = "ID der zu aktualisierenden Erinnerung.", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Neue Informationen der Erinnerung.")
            @RequestBody ReminderUpdateDto reminderUpdateDto) {
        ReminderShowDto updatedReminder = reminderService.update(id, reminderUpdateDto);
        if (updatedReminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedReminder);
    }

    /**
     * Löscht eine Erinnerung anhand der ID.
     * Methode: DELETE
     * URL: http://localhost:8080/reminders/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Löscht eine Erinnerung anhand der ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Erinnerung erfolgreich gelöscht."),
            @ApiResponse(responseCode = "404", description = "Erinnerung nicht gefunden.")
    })
    public ResponseEntity<Void> deleteReminder(
            @Parameter(description = "ID der zu löschenden Erinnerung.", example = "1")
            @PathVariable Integer id) {
        ReminderShowDto reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        reminderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}