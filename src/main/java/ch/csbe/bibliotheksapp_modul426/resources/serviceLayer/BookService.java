package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import ch.csbe.bibliotheksapp_modul426.resources.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Alle Bücher abrufen
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // Buch nach ID finden
    public Book findById(Integer id) {
        return bookRepository.findById(id)
                .orElse(null);  // Rückgabe von null, wenn das Buch nicht gefunden wird
    }

    // Neues Buch hinzufügen
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // Buch aktualisieren
    public Book update(Integer id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElse(null);  // Buch mit der ID finden, oder null, wenn nicht vorhanden
        if (book != null) {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setAvailable(bookDetails.isAvailable());
            return bookRepository.save(book);
        }
        return null;  // Rückgabe von null, wenn das Buch nicht gefunden wurde
    }

    // Buch löschen
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}