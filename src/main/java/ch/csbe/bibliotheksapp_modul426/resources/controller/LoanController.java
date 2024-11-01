package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.Loan.LoanUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.LoanService;
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
    @Operation(summary = "Gibt eine Liste aller Ausleihen zurück.")
    @ApiResponse(responseCode = "200", description = "Erfolgreich die Liste aller Ausleihen zurückgegeben.")
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
    @Operation(summary = "Gibt die Details einer bestimmten Ausleihe zurück.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ausleihedetails erfolgreich zurückgegeben."),
            @ApiResponse(responseCode = "404", description = "Ausleihe nicht gefunden.")
    })
    public ResponseEntity<LoanShowDto> getLoanById(
            @Parameter(description = "ID der Ausleihe, die abgerufen werden soll.", example = "1")
            @PathVariable Integer id) {
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
    @Operation(summary = "Erstellt eine neue Ausleihe.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ausleihe erfolgreich erstellt."),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten.")
    })
    public ResponseEntity<LoanShowDto> createLoan(
            @Parameter(description = "Details der neuen Ausleihe.")
            @RequestBody LoanCreateDto loanCreateDto) {
        LoanShowDto createdLoan = loanService.save(loanCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    /**
     * Löscht eine Ausleihe anhand der ID.
     * Methode: DELETE
     * URL: http://localhost:8080/loans/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Löscht eine Ausleihe anhand der ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ausleihe erfolgreich gelöscht."),
            @ApiResponse(responseCode = "404", description = "Ausleihe nicht gefunden.")
    })
    public ResponseEntity<Void> deleteLoan(
            @Parameter(description = "ID der zu löschenden Ausleihe.", example = "1")
            @PathVariable Integer id) {
        LoanShowDto loan = loanService.findById(id);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
