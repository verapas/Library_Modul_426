package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
import ch.csbe.bibliotheksapp_modul426.resources.mapper.BookMapper;
import ch.csbe.bibliotheksapp_modul426.resources.mapper.LoanMapper;
import ch.csbe.bibliotheksapp_modul426.resources.repository.BookRepository;
import ch.csbe.bibliotheksapp_modul426.resources.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private LoanMapper loanMapper;

    // Alle Bücher abrufen
    public List<BookShowDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookShowDto)
                .collect(Collectors.toList());
    }

    // Buch nach ID finden
    public BookDetailDto findById(Integer id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookDetailDto) // Konvertierung in DTO falls gefunden
                .orElse(null); // Rückgabe von null, wenn nicht gefunden
    }


    // Neues Buch erstellen
    public BookShowDto save(BookCreateDto bookCreateDto) {
        Book book = bookMapper.toBookEntity(bookCreateDto);
        book.setAvailable(true);  // Setzt den Standardwert für Verfügbarkeit
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookShowDto(savedBook);
    }

    // Buch aktualisieren
    public BookShowDto update(Integer id, BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookMapper.updateBookEntity(bookUpdateDto, book);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toBookShowDto(updatedBook);
    }

    // Buch löschen
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    public LoanShowDto returnBookByBookId(Integer bookId) {
        // Finde das aktuell ausgeliehene Loan für das Buch
        Loan loan = loanRepository.findByBookIdAndStatus(bookId, "Borrowed")
                .orElseThrow(() -> new RuntimeException("Kein ausgeliehener Zustand für dieses Buch gefunden"));

        // Setze das Rückgabedatum auf das heutige Datum und ändere den Status
        loan.setReturnDate(LocalDate.now().toString());
        loan.setStatus("Returned");

        // Setze das Buch auf "verfügbar"
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        // Speichere die aktualisierte Ausleihe und gib das LoanShowDto zurück
        Loan updatedLoan = loanRepository.save(loan);
        return loanMapper.toLoanShowDto(updatedLoan);
    }
}
