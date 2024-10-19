package ch.csbe.bibliotheksapp_modul426.resources.repository;

import ch.csbe.bibliotheksapp_modul426.resources.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Benutzerdefinierte Abfrage, um einen Benutzer anhand seiner E-Mail zu finden
    Optional<User> findByEmail(String email);

    // Jenachdem werden wir noch weitere Benutzerdefinierte Abdfragen hinzufügen
    // Das JpaRepository-Interface bietet bereits vordefinierte Methoden für CRUD-Operationen wie save(),
    // findAll(), findById(), delete() usw.
}