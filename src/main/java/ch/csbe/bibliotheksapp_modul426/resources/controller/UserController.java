package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Gibt eine Liste aller Benutzer zurück.
     * Methode: GET
     * URL: http://localhost:8080/users
     */
    @GetMapping
    public ResponseEntity<List<UserShowDto>> getAllUsers() {
        List<UserShowDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Gibt die Details eines Benutzers anhand der ID zurück.
     * Methode: GET
     * URL: http://localhost:8080/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getUserById(@PathVariable Integer id) {
        UserDetailDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Registriert einen neuen Benutzer.
     * Methode: POST
     * URL: http://localhost:8080/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserShowDto> registerUser(@RequestBody UserCreateDto userCreateDto) {
        UserShowDto registeredUser = userService.registerUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    /**
     * Authentifiziert einen Benutzer basierend auf E-Mail und Passwort.
     * Methode: POST
     * URL: http://localhost:8080/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<UserShowDto> loginUser(@RequestParam String email, @RequestParam String password) {
        UserShowDto user = userService.authenticateUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Aktualisiert die Informationen eines Benutzers.
     * Methode: PUT
     * URL: http://localhost:8080/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserShowDto> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDto userUpdateDto) {
        UserShowDto updatedUser = userService.update(id, userUpdateDto);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Löscht einen Benutzer anhand der ID.
     * Methode: DELETE
     * URL: http://localhost:8080/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        UserDetailDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}