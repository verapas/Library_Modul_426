package ch.csbe.bibliotheksapp_modul426.resources.entities.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private LoanRepository loanRepository;

    // Methode zum Erstellen einer neuen Erinnerung
    public Reminder createReminder(int loanId) {
        // Finde die Ausleihe (Loan) über loanId
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Erstelle eine neue Erinnerung (Reminder)
        Reminder reminder = new Reminder();
        reminder.setLoan(loan);
        reminder.setEmailSent(false);  // Standardmäßig ist die E-Mail noch nicht gesendet

        // Speichere die Erinnerung in der Datenbank
        return reminderRepository.save(reminder);
    }

    // Methode zum Markieren der Erinnerung als "E-Mail gesendet"
    public Reminder markEmailAsSent(int reminderId) {
        // Finde die Erinnerung über reminderId
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        // Setze das Feld "emailSent" auf true, um zu kennzeichnen, dass die E-Mail gesendet wurde
        reminder.setEmailSent(true);

        // Speichere die aktualisierte Erinnerung in der Datenbank
        return reminderRepository.save(reminder);
    }

    // Methode zum Abrufen aller Erinnerungen für eine bestimmte Loan (optional)
    public List<Reminder> getRemindersForLoan(int loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return reminderRepository.findByLoan(loan);
    }
}
