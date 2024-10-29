package ch.csbe.bibliotheksapp_modul426.resources.dto.Book;

import lombok.Data;
import java.util.List;

@Data
public class BookDetailDto {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean available;
    private List<LoanDto> loans;  // Liste an wen schon verleiht etc.

    @Data
    public static class LoanDto {
        private int id;
        private String loanDate;
        private String returnDate;
        private String status;
        private int userId;
    }
}