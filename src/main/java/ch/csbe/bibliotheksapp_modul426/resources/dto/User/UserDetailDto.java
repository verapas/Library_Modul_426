package ch.csbe.bibliotheksapp_modul426.resources.dto.User;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailDto {
    private int id;
    private String name;
    private String email;
    private String role;
    private List<LoanDto> loans;

    @Data
    public static class LoanDto {
        private int id;
        private int bookId;
        private String loanDate;
        private String returnDate;
        private String status;
    }
}