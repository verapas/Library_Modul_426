package ch.csbe.bibliotheksapp_modul426.resources.dto.Book;

import lombok.Data;

@Data
public class BookUpdateDto {
    private String title;
    private String author;
    private String isbn;
    private boolean available;
}