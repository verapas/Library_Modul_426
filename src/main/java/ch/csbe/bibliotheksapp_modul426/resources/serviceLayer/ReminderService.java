package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.mapper.ReminderMapper;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder.ReminderShowDto;
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

    @Autowired
    private ReminderMapper reminderMapper;

    // Alle Erinnerungen abrufen
    public List<ReminderShowDto> findAll() {
        return reminderRepository.findAll().stream()
                .map(reminderMapper::toReminderDto)
                .collect(Collectors.toList());
    }

    // Erinnerung nach ID finden
    public ReminderShowDto findById(int reminderId) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElse(null);  // Gibt null zurück, wenn keine Erinnerung gefunden wird
        return reminder != null ? reminderMapper.toReminderDto(reminder) : null;
    }

    // Methode zum Erstellen einer neuen Erinnerung
    public ReminderShowDto save(ReminderCreateDto reminderCreateDto) {
        Loan loan = loanRepository.findById(reminderCreateDto.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Reminder reminder = reminderMapper.toReminderEntity(reminderCreateDto);
        reminder.setEmailSent(false); // Standardmäßig ist die E-Mail noch nicht gesendet

        Reminder savedReminder = reminderRepository.save(reminder);
        return reminderMapper.toReminderDto(savedReminder);
    }

    // Methode zum Aktualisieren einer Erinnerung
    public ReminderShowDto update(int reminderId, ReminderUpdateDto reminderUpdateDto) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminderMapper.updateReminderEntity(reminderUpdateDto, reminder);

        Reminder updatedReminder = reminderRepository.save(reminder);
        return reminderMapper.toReminderDto(updatedReminder);
    }

    // Erinnerung löschen
    public void delete(int reminderId) {
        reminderRepository.deleteById(reminderId);
    }
}
