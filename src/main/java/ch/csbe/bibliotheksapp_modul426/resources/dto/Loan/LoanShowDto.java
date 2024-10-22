package ch.csbe.bibliotheksapp_modul426.resources.dto.Loan;

import lombok.Data;

@Data
public class LoanShowDto {
    private int id;
    private int bookId;
    private String bookTitle;
    private int userId;
    private String userName;
    private String loanDate;
    private String returnDate;
    private String status;
}