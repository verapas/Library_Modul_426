package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Book.BookDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
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

    // Alle Bücher abrufen
    public List<BookShowDto> findAll() {
        return bookRepository.findAll().stream()
                .map(this::toBookShowDto)
                .collect(Collectors.toList());
    }

    // Buch nach ID finden
    public BookDetailDto findById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElse(null);  // Rückgabe von null, wenn das Buch nicht gefunden wird
        return book != null ? toBookDetailDto(book) : null;
    }

    // Neues Buch hinzufügen
    public BookShowDto save(BookCreateDto bookCreateDto) {
        Book book = new Book();
        book.setTitle(bookCreateDto.getTitle());
        book.setAuthor(bookCreateDto.getAuthor());
        book.setIsbn(bookCreateDto.getIsbn());
        book.setAvailable(true);  // Standardmäßig auf true setzen

        Book savedBook = bookRepository.save(book);
        return toBookShowDto(savedBook);
    }

    // Buch aktualisieren
    public BookShowDto update(Integer id, BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));  // Buch mit der ID finden

        book.setTitle(bookUpdateDto.getTitle());
        book.setAuthor(bookUpdateDto.getAuthor());
        book.setIsbn(bookUpdateDto.getIsbn());
        book.setAvailable(bookUpdateDto.isAvailable());

        Book updatedBook = bookRepository.save(book);
        return toBookShowDto(updatedBook);
    }

    // Buch löschen
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    // Umwandlung von Book in BookShowDto
    private BookShowDto toBookShowDto(Book book) {
        BookShowDto bookShowDto = new BookShowDto();
        bookShowDto.setId(book.getId());
        bookShowDto.setTitle(book.getTitle());
        bookShowDto.setAuthor(book.getAuthor());
        bookShowDto.setIsbn(book.getIsbn());
        bookShowDto.setAvailable(book.isAvailable());
        return bookShowDto;
    }

    // Umwandlung von Book in BookDetailDto
    private BookDetailDto toBookDetailDto(Book book) {
        BookDetailDto bookDetailDto = new BookDetailDto();
        bookDetailDto.setId(book.getId());
        bookDetailDto.setTitle(book.getTitle());
        bookDetailDto.setAuthor(book.getAuthor());
        bookDetailDto.setIsbn(book.getIsbn());
        bookDetailDto.setAvailable(book.isAvailable());

        List<Loan> loans = loanRepository.findByBookId(book.getId());
        List<BookDetailDto.LoanDto> loanDtos = loans.stream()
                .map(loan -> {
                    BookDetailDto.LoanDto loanDto = new BookDetailDto.LoanDto();
                    loanDto.setId(loan.getId());
                    loanDto.setLoanDate(loan.getLoanDate());
                    loanDto.setReturnDate(loan.getReturnDate());
                    loanDto.setStatus(loan.getStatus());
                    loanDto.setUserId(loan.getUser().getId());
                    return loanDto;
                })
                .collect(Collectors.toList());

        bookDetailDto.setLoans(loanDtos);

        return bookDetailDto;
    }
}