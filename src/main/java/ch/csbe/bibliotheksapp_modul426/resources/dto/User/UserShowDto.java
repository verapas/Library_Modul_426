package ch.csbe.bibliotheksapp_modul426.resources.dto.User;

import lombok.Data;

@Data
public class UserShowDto {
    private int id;
    private String name;
    private String email;
    private String role;
}