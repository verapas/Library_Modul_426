package ch.csbe.bibliotheksapp_modul426.resources.repository;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    // Benutzerdefinierte Abfrage, um alle Loans eines Benutzers zu finden
    List<Loan> findByUserId(Integer userId);

    List<Loan> findByBookId(Integer bookId);

    // Methode zur Suche nach Buch-ID und Status
    Optional<Loan> findByBookIdAndStatus(Integer bookId, String status);

    // Jenachdem werden wir noch weitere Benutzerdefinierte Abdfragen hinzufügen
    // Das JpaRepository-Interface bietet bereits vordefinierte Methoden für CRUD-Operationen wie save(),
    // findAll(), findById(), delete() usw.
}
