package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    /**
     * Gibt eine Liste aller Ausleihen zurück.
     * Methode: GET
     * URL: http://localhost:8080/loans
     */
    @GetMapping
    public ResponseEntity<List<LoanShowDto>> getAllLoans() {
        List<LoanShowDto> loans = loanService.findAll();
        return ResponseEntity.ok(loans);
    }

    /**
     * Gibt die Details einer Ausleihe anhand der ID zurück.
     * Methode: GET
     * URL: http://localhost:8080/loans/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanShowDto> getLoanById(@PathVariable Integer id) {
        LoanShowDto loan = loanService.findById(id);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(loan);
    }

    /**
     * Erstellt eine neue Ausleihe.
     * Methode: POST
     * URL: http://localhost:8080/loans
     */
    @PostMapping
    public ResponseEntity<LoanShowDto> createLoan(@RequestBody LoanCreateDto loanCreateDto) {
        LoanShowDto createdLoan = loanService.save(loanCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    /**
     * Aktualisiert die Informationen einer bestehenden Ausleihe.
     * Methode: PUT
     * URL: http://localhost:8080/loans/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanShowDto> updateLoan(@PathVariable Integer id, @RequestBody LoanUpdateDto loanUpdateDto) {
        LoanShowDto updatedLoan = loanService.update(id, loanUpdateDto);
        if (updatedLoan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedLoan);
    }

    /**
     * Löscht eine Ausleihe anhand der ID.
     * Methode: DELETE
     * URL: http://localhost:8080/loans/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        LoanShowDto loan = loanService.findById(id);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}