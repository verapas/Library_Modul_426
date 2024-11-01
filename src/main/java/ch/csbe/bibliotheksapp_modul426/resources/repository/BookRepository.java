package ch.csbe.bibliotheksapp_modul426.resources.repository;

import ch.csbe.bibliotheksapp_modul426.resources.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Benutzerdefinierte Abfrage, um ein Buch anhand der ISBN zu finden
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByGenre(String genre);

    // Jenachdem werden wir noch weitere Benutzerdefinierte Abdfragen hinzufügen
    // Das JpaRepository-Interface bietet bereits vordefinierte Methoden für CRUD-Operationen wie save(),
    // findAll(), findById(), delete() usw.
}