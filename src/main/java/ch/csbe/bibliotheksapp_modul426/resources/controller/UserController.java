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

    // Alle Benutzer abrufen
    @GetMapping
    public ResponseEntity<List<UserShowDto>> getAllUsers() {
        List<UserShowDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // Benutzer nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getUserById(@PathVariable Integer id) {
        UserDetailDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    // Neuen Benutzer erstellen
    @PostMapping
    public ResponseEntity<UserShowDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        UserShowDto createdUser = userService.save(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Benutzer aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<UserShowDto> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDto userUpdateDto) {
        UserShowDto updatedUser = userService.update(id, userUpdateDto);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    // Benutzer löschen
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