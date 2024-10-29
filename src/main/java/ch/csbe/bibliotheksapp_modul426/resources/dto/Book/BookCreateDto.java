package ch.csbe.bibliotheksapp_modul426.resources.dto.Book;

import lombok.Data;

@Data
public class BookCreateDto {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    // available sollte standartmässig auf True gesetzt werden (serviceLayer)
}