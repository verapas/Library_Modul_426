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

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Methode für das Ausleihen eines Buches
    public Loan borrowBook(int userId, int bookId) {
        // Finde das Buch
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Prüfe, ob das Buch verfügbar ist
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        // Finde den Benutzer
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Erstelle eine neue Loan (Ausleihe)
        Loan loan = new Loan();
        loan.setUser(user);  // Verknüpfe die Ausleihe mit dem Benutzer
        loan.setBook(book);  // Verknüpfe die Ausleihe mit dem Buch
        loan.setLoanDate(LocalDate.now().toString());
        loan.setStatus("Borrowed");

        // Setze das Buch auf "nicht verfügbar"
        book.setAvailable(false);
        bookRepository.save(book);  // Speichere die Änderung am Buch

        return loanRepository.save(loan);  // Speichere die Ausleihe
    }

    // Methode für die Rückgabe eines Buches
    public Loan returnBook(int loanId) {
        // Finde die Ausleihe
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Finde das zugehörige Buch
        Book book = loan.getBook();

        // Setze das Buch auf "verfügbar"
        book.setAvailable(true);
        bookRepository.save(book);  // Speichere die Änderung am Buch

        // Aktualisiere die Ausleihe mit dem Rückgabedatum
        loan.setReturnDate(LocalDate.now().toString());
        loan.setStatus("returned");

        return loanRepository.save(loan);  // Speichere die aktualisierte Ausleihe
    }
}
