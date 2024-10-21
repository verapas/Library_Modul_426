package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Reminder;
import ch.csbe.bibliotheksapp_modul426.resources.repository.LoanRepository;
import ch.csbe.bibliotheksapp_modul426.resources.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private LoanRepository loanRepository;

    // Alle Erinnerungen abrufen
    public List<ReminderDto> findAll() {
        return reminderRepository.findAll().stream()
                .map(this::toReminderDto)
                .collect(Collectors.toList());
    }

    // Erinnerung nach ID finden
    public ReminderDto findById(int reminderId) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElse(null);  // Gibt null zurück, wenn keine Erinnerung gefunden wird
        return reminder != null ? toReminderDto(reminder) : null;
    }

    // Methode zum Erstellen einer neuen Erinnerung
    public ReminderDto save(ReminderCreateDto reminderCreateDto) {
        Loan loan = loanRepository.findById(reminderCreateDto.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Reminder reminder = new Reminder();
        reminder.setLoan(loan);
        reminder.setEmailSent(false); // Standardmäßig ist die E-Mail noch nicht gesendet

        Reminder savedReminder = reminderRepository.save(reminder);
        return toReminderDto(savedReminder);
    }

    // Methode zum Aktualisieren einer Erinnerung (z.B. Markieren der E-Mail als gesendet)
    public ReminderDto update(int reminderId, ReminderUpdateDto reminderUpdateDto) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminder.setEmailSent(reminderUpdateDto.isEmailSent());

        Reminder updatedReminder = reminderRepository.save(reminder);
        return toReminderDto(updatedReminder);
    }

    // Erinnerung löschen
    public void delete(int reminderId) {
        reminderRepository.deleteById(reminderId);
    }

    // Umwandlung von Reminder in ReminderDto
    private ReminderDto toReminderDto(Reminder reminder) {
        ReminderDto reminderDto = new ReminderDto();
        reminderDto.setId(reminder.getId());
        reminderDto.setEmailSent(reminder.isEmailSent());
        reminderDto.setLoanId(reminder.getLoan().getId());
        return reminderDto;
    }
}