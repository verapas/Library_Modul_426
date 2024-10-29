package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Gibt eine Liste aller Bücher zurück.")
    @ApiResponse(responseCode = "200", description = "Erfolgreich die Liste aller Bücher zurückgegeben.")
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
    @Operation(summary = "Gibt die Details eines bestimmten Buches zurück.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buchdetails erfolgreich zurückgegeben."),
            @ApiResponse(responseCode = "404", description = "Buch nicht gefunden.")
    })
    public ResponseEntity<BookDetailDto> getBookById(
            @Parameter(description = "ID des Buches, das abgerufen werden soll.", example = "1")
            @PathVariable Integer id) {
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
    @Operation(summary = "Erstellt ein neues Buch.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Buch erfolgreich erstellt."),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten.")
    })
    public ResponseEntity<BookShowDto> createBook(
            @Parameter(description = "Details des zu erstellenden Buches.")
            @RequestBody BookCreateDto bookCreateDto) {
        BookShowDto createdBook = bookService.save(bookCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * Aktualisiert die Informationen eines bestehenden Buches.
     * Methode: PUT
     * URL: http://localhost:8080/books/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Aktualisiert ein bestehendes Buch.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buch erfolgreich aktualisiert."),
            @ApiResponse(responseCode = "404", description = "Buch nicht gefunden."),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten.")
    })
    public ResponseEntity<BookShowDto> updateBook(
            @Parameter(description = "ID des zu aktualisierenden Buches.", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Aktualisierte Details des Buches.")
            @RequestBody BookUpdateDto bookUpdateDto) {
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
    @Operation(summary = "Löscht ein Buch anhand der ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Buch erfolgreich gelöscht."),
            @ApiResponse(responseCode = "404", description = "Buch nicht gefunden.")
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID des zu löschenden Buches.", example = "1")
            @PathVariable Integer id) {
        BookDetailDto book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gibt ein Buch zurück, basierend auf der Buch-ID.
     * Methode: PUT
     * URL: http://localhost:8080/books/{bookId}/return
     */
    @PutMapping("/{bookId}/return")
    @Operation(summary = "Gibt ein Buch zurück, basierend auf der Buch-ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buch erfolgreich zurückgegeben."),
            @ApiResponse(responseCode = "404", description = "Buch nicht gefunden.")
    })
    public ResponseEntity<LoanShowDto> returnBook(
            @Parameter(description = "ID des zurückzugebenden Buches.", example = "1")
            @PathVariable Integer bookId) {
        LoanShowDto updatedLoan = bookService.returnBookByBookId(bookId);
        return ResponseEntity.ok(updatedLoan);
    }
}
