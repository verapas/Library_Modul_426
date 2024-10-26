package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Gibt eine Liste aller Bücher zurück.
     * Methode: GET
     * URL: http://localhost:8080/books
     */
    @GetMapping
    public ResponseEntity<List<BookShowDto>> getAllBooks() {
        List<BookShowDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    /**
     * Gibt die Details eines Buches anhand der ID zurück.
     * Methode: GET
     * URL: http://localhost:8080/books/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailDto> getBookById(@PathVariable Integer id) {
        BookDetailDto book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(book);
    }

    /**
     * Erstellt ein neues Buch.
     * Methode: POST
     * URL: http://localhost:8080/books
     */
    @PostMapping
    public ResponseEntity<BookShowDto> createBook(@RequestBody BookCreateDto bookCreateDto) {
        BookShowDto createdBook = bookService.save(bookCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * Aktualisiert die Informationen eines bestehenden Buches.
     * Methode: PUT
     * URL: http://localhost:8080/books/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookShowDto> updateBook(@PathVariable Integer id, @RequestBody BookUpdateDto bookUpdateDto) {
        BookShowDto updatedBook = bookService.update(id, bookUpdateDto);
        if (updatedBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Löscht ein Buch anhand der ID.
     * Methode: DELETE
     * URL: http://localhost:8080/books/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        BookDetailDto book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
