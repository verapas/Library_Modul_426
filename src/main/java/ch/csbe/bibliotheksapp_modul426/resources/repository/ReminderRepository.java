package ch.csbe.bibliotheksapp_modul426.resources.repository;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Integer> {

    // Benutzerdefinierte Abfrage, um alle Erinnerungen für eine bestimmte Loan zu finden
    List<Reminder> findByLoanId(Integer loanId);

    // Jenachdem werden wir noch weitere Benutzerdefinierte Abdfragen hinzufügen
    // Das JpaRepository-Interface bietet bereits vordefinierte Methoden für CRUD-Operationen wie save(),
    // findAll(), findById(), delete() usw.
}
