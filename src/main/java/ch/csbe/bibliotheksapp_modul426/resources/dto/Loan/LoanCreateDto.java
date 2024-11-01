package ch.csbe.bibliotheksapp_modul426.resources.dto.Loan;

import lombok.Data;

@Data
public class LoanCreateDto {
    private int bookId;
    private int userId;
}