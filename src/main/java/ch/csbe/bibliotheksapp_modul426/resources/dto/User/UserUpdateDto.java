package ch.csbe.bibliotheksapp_modul426.resources.dto.User;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    private String email;
    private String password;
    private String role;
}