package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanUpdateDto;
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
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Alle Ausleihen abrufen
    public List<LoanShowDto> findAll() {
        return loanRepository.findAll().stream()
                .map(this::toLoanShowDto)
                .collect(Collectors.toList());
    }

    // Ausleihe nach ID finden
    public LoanShowDto findById(int loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        return loan != null ? toLoanShowDto(loan) : null;
    }

    // Methode für das Ausleihen eines Buches
    public LoanShowDto save(LoanCreateDto loanCreateDto) {
        Book book = bookRepository.findById(loanCreateDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        User user = userRepository.findById(loanCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now().toString());
        loan.setStatus("Borrowed");

        book.setAvailable(false);
        bookRepository.save(book);

        return toLoanShowDto(loanRepository.save(loan));
    }

    // Methode für die Rückgabe eines Buches
    public LoanShowDto update(int loanId, LoanUpdateDto loanUpdateDto) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Book book = loan.getBook();

        if (loanUpdateDto.getReturnDate() != null) {
            book.setAvailable(true);
            bookRepository.save(book);
            loan.setReturnDate(loanUpdateDto.getReturnDate());
            loan.setStatus("Returned");
        } else {
            loan.setStatus(loanUpdateDto.getStatus());
        }

        return toLoanShowDto(loanRepository.save(loan));
    }

    // Ausleihe löschen
    public void delete(int loanId) {
        loanRepository.deleteById(loanId);
    }

    private LoanShowDto toLoanShowDto(Loan loan) {
        LoanShowDto loanShowDto = new LoanShowDto();
        loanShowDto.setId(loan.getId());
        loanShowDto.setBookId(loan.getBook().getId());
        loanShowDto.setBookTitle(loan.getBook().getTitle());
        loanShowDto.setUserId(loan.getUser().getId());
        loanShowDto.setUserName(loan.getUser().getName());
        loanShowDto.setLoanDate(loan.getLoanDate());
        loanShowDto.setReturnDate(loan.getReturnDate());
        loanShowDto.setStatus(loan.getStatus());
        return loanShowDto;
    }
}