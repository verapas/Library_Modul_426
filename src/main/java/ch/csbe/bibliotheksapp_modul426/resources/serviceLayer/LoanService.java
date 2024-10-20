package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import ch.csbe.bibliotheksapp_modul426.resources.entities.User;
import ch.csbe.bibliotheksapp_modul426.resources.repository.BookRepository;
import ch.csbe.bibliotheksapp_modul426.resources.repository.LoanRepository;
import ch.csbe.bibliotheksapp_modul426.resources.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Alle Ausleihen abrufen
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    // Ausleihe nach ID finden
    public Loan findById(int loanId) {
        return loanRepository.findById(loanId)
                .orElse(null); // Gibt null zurück, wenn keine Ausleihe gefunden wird
    }

    // Methode für das Ausleihen eines Buches
    public Loan save(Loan loan) {
        // Finde das Buch basierend auf der Buch-ID im Loan-Objekt
        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Prüfe, ob das Buch verfügbar ist
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        // Finde den Benutzer basierend auf der Benutzer-ID im Loan-Objekt
        User user = userRepository.findById(loan.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verknüpfe die Ausleihe mit dem gefundenen Benutzer und Buch
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now().toString());  // Setze das aktuelle Datum
        loan.setStatus("Borrowed");

        // Setze das Buch auf "nicht verfügbar"
        book.setAvailable(false);
        bookRepository.save(book);  // Speichere die Änderung am Buch

        // Speichere die Ausleihe und gib sie zurück
        return loanRepository.save(loan);
    }

    // Methode für die Rückgabe eines Buches
    public Loan update(int loanId, Loan updatedLoan) {
        // Finde die bestehende Ausleihe
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Finde das zugehörige Buch
        Book book = loan.getBook();

        // Wenn das Buch in der aktualisierten Ausleihe als zurückgegeben markiert wurde
        if (updatedLoan.getReturnDate() != null) {
            // Setze das Buch auf "verfügbar"
            book.setAvailable(true);
            bookRepository.save(book);  // Speichere die Änderung am Buch

            // Aktualisiere die Ausleihe mit dem Rückgabedatum und Status
            loan.setReturnDate(LocalDate.now().toString());
            loan.setStatus("Returned");
        } else {
            // Falls keine Rückgabe, aktualisiert nur andere relevante Felder der Ausleihe
            loan.setStatus(updatedLoan.getStatus());
            loan.setLoanDate(updatedLoan.getLoanDate());
        }

        return loanRepository.save(loan);  // Speichere die aktualisierte Ausleihe
    }

    // Ausleihe löschen
    public void delete(int loanId) {
        loanRepository.deleteById(loanId);
    }
}