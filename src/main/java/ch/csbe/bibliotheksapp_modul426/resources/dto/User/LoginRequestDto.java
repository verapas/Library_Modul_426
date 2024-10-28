package ch.csbe.bibliotheksapp_modul426.resources.dto.User;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}