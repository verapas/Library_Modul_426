package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import ch.csbe.bibliotheksapp_modul426.resources.mapper.BookMapper;
import ch.csbe.bibliotheksapp_modul426.resources.repository.BookRepository;
import ch.csbe.bibliotheksapp_modul426.resources.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Alle Bücher abrufen
    public List<BookShowDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookShowDto)
                .collect(Collectors.toList());
    }

    // Buch nach ID finden
    public BookDetailDto findById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElse(null);
        if (book != null) {

            return null;  // BookDetailDto wird noch angepasst
        }
        return null;
    }

    // Neues Buch erstellen
    public BookShowDto save(BookCreateDto bookCreateDto) {
        Book book = bookMapper.toBookEntity(bookCreateDto);
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
}