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

    // Alle Bücher abrufen
    @GetMapping
    public ResponseEntity<List<BookShowDto>> getAllBooks() {
        List<BookShowDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    // Buch nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailDto> getBookById(@PathVariable Integer id) {
        BookDetailDto book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(book);
    }

    // Neues Buch erstellen
    @PostMapping
    public ResponseEntity<BookShowDto> createBook(@RequestBody BookCreateDto bookCreateDto) {
        BookShowDto createdBook = bookService.save(bookCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    // Buch aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<BookShowDto> updateBook(@PathVariable Integer id, @RequestBody BookUpdateDto bookUpdateDto) {
        BookShowDto updatedBook = bookService.update(id, bookUpdateDto);
        if (updatedBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    // Buch löschen
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