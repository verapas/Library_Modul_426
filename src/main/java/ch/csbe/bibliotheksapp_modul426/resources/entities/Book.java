package ch.csbe.bibliotheksapp_modul426.resources.entities;
import lombok.Data;


@Data
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    private Integer borrowedBy; // Nullable, kann die ID des Benutzers enthalten
    private String borrowedDate; // FORMAT: YYYY-MM-DD
    private String returnDate; // FORMAT: YYYY-MM-DD
}