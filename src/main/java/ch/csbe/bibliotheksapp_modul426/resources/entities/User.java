package ch.csbe.bibliotheksapp_modul426.resources.entities;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role; // Enum für 'staff' und 'customer'
}
