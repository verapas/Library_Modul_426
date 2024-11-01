package ch.csbe.bibliotheksapp_modul426.resources.dto.Book;

import lombok.Data;

@Data
public class BookShowDto {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean available;
}