package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.ReminderService;
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

    // Alle Erinnerungen abrufen
    @GetMapping
    public ResponseEntity<List<ReminderShowDto>> getAllReminders() {
        List<ReminderShowDto> reminders = reminderService.findAll();
        return ResponseEntity.ok(reminders);
    }

    // Erinnerung nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<ReminderShowDto> getReminderById(@PathVariable Integer id) {
        ReminderShowDto reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reminder);
    }

    // Neue Erinnerung erstellen
    @PostMapping
    public ResponseEntity<ReminderShowDto> createReminder(@RequestBody ReminderCreateDto reminderCreateDto) {
        ReminderShowDto createdReminder = reminderService.save(reminderCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReminder);
    }

    // Erinnerung aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<ReminderShowDto> updateReminder(@PathVariable Integer id, @RequestBody ReminderUpdateDto reminderUpdateDto) {
        ReminderShowDto updatedReminder = reminderService.update(id, reminderUpdateDto);
        if (updatedReminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedReminder);
    }

    // Erinnerung löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Integer id) {
        ReminderShowDto reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        reminderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}