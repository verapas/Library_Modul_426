package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Reminder;
import ch.csbe.bibliotheksapp_modul426.resources.repository.LoanRepository;
import ch.csbe.bibliotheksapp_modul426.resources.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private LoanRepository loanRepository;

    // Alle Erinnerungen abrufen
    public List<Reminder> findAll() {
        return reminderRepository.findAll();
    }

    // Erinnerung nach ID finden
    public Reminder findById(int reminderId) {
        return reminderRepository.findById(reminderId)
                .orElse(null);  // Gibt null zurück, wenn keine Erinnerung gefunden wird
    }

    // Methode zum Erstellen einer neuen Erinnerung
    public Reminder save(Reminder reminder) {
        // Finde die Ausleihe (Loan) über loanId aus dem Reminder-Objekt
        Loan loan = loanRepository.findById(reminder.getLoan().getId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Verknüpfe die Erinnerung mit der Ausleihe
        reminder.setLoan(loan);
        reminder.setEmailSent(false);  // Standardmäßig ist die E-Mail noch nicht gesendet

        // Speichere die Erinnerung in der Datenbank
        return reminderRepository.save(reminder);
    }

    // Methode zum Aktualisieren einer Erinnerung (z.B. Markieren der E-Mail als gesendet)
    public Reminder update(int reminderId, Reminder updatedReminder) {
        // Finde die Erinnerung über reminderId
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        // Aktualisiere die Erinnerung (hier speziell das "emailSent"-Feld)
        reminder.setEmailSent(updatedReminder.isEmailSent());

        return reminderRepository.save(reminder);
    }

    // Erinnerung löschen
    public void delete(int reminderId) {
        reminderRepository.deleteById(reminderId);
    }
}