package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Reminder;
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
    public ResponseEntity<List<Reminder>> getAllReminders() {
        List<Reminder> reminders = reminderService.findAll();
        return ResponseEntity.ok(reminders);
    }

    // Erinnerung nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable Integer id) {
        Reminder reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reminder);
    }

    // Neue Erinnerung erstellen
    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        Reminder createdReminder = reminderService.save(reminder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReminder);
    }

    // Erinnerung aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable Integer id, @RequestBody Reminder updatedReminder) {
        Reminder reminder = reminderService.update(id, updatedReminder);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reminder);
    }

    // Erinnerung löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Integer id) {
        Reminder reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        reminderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}