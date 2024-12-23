package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.mapper.LoanMapper;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.*;
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

    @Autowired
    private LoanMapper loanMapper;

    // Alle Ausleihen abrufen
    public List<LoanShowDto> findAll() {
        return loanRepository.findAll().stream()
                .map(loanMapper::toLoanShowDto)
                .collect(Collectors.toList());
    }

    // Ausleihe nach ID finden
    public LoanShowDto findById(int loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        return loan != null ? loanMapper.toLoanShowDto(loan) : null;
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

        Loan loan = loanMapper.toLoanEntity(loanCreateDto);
        loan.setLoanDate(LocalDate.now().toString());
        loan.setStatus("Borrowed");

        book.setAvailable(false);
        bookRepository.save(book);

        return loanMapper.toLoanShowDto(loanRepository.save(loan));
    }


    // Ausleihe löschen
    public void delete(int loanId) {
        loanRepository.deleteById(loanId);
    }
}
