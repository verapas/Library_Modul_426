package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.entities.User;
import ch.csbe.bibliotheksapp_modul426.resources.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Alle Benutzer abrufen
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Benutzer nach ID finden
    public User findById(int id) {
        return userRepository.findById(id)
                .orElse(null);  // Gibt null zurück, wenn der Benutzer nicht gefunden wird
    }

    // Methode zum Erstellen eines neuen Benutzers
    public User save(User user) {
        return userRepository.save(user);
    }

    // Methode zum Aktualisieren eines Benutzers
    public User update(int id, User userDetails) {
        User user = userRepository.findById(id)
                .orElse(null);  // Benutzer suchen oder null, wenn nicht gefunden
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);  // Benutzer aktualisieren
        }
        return null;  // Falls Benutzer nicht gefunden wurde
    }

    // Benutzer löschen
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}

