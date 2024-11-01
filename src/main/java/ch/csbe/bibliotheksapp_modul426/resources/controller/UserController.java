package ch.csbe.bibliotheksapp_modul426.resources.controller;

import ch.csbe.bibliotheksapp_modul426.resources.dto.User.*;
import ch.csbe.bibliotheksapp_modul426.resources.serviceLayer.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Gibt eine Liste aller Benutzer zurück")
    @ApiResponse(responseCode = "200", description = "Benutzerliste erfolgreich abgerufen")
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
    @Operation(summary = "Gibt die Details eines Benutzers anhand der ID zurück")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Benutzerdetails erfolgreich abgerufen"),
            @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden")
    })
    public ResponseEntity<UserDetailDto> getUserById(
            @Parameter(description = "ID des Benutzers", example = "1")
            @PathVariable Integer id) {
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
    @Operation(summary = "Registriert einen neuen Benutzer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Benutzer erfolgreich registriert"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten")
    })
    public ResponseEntity<UserShowDto> registerUser(
            @Parameter(description = "Daten des zu registrierenden Benutzers")
            @RequestBody UserCreateDto userCreateDto) {
        UserShowDto registeredUser = userService.registerUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    /**
     * Authentifiziert einen Benutzer basierend auf E-Mail und Passwort.
     * Methode: POST
     * URL: http://localhost:8080/users/login
     */
    @PostMapping("/login")
    @Operation(summary = "Authentifiziert einen Benutzer basierend auf E-Mail und Passwort")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich angemeldet"),
            @ApiResponse(responseCode = "401", description = "Ungültige Anmeldedaten")
    })
    public ResponseEntity<UserShowDto> loginUser(
            @Parameter(description = "Anmeldedaten des Benutzers")
            @RequestBody LoginRequestDto loginRequestDto) {
        UserShowDto user = userService.authenticateUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
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
    @Operation(summary = "Aktualisiert die Informationen eines Benutzers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich aktualisiert"),
            @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden")
    })
    public ResponseEntity<UserShowDto> updateUser(
            @Parameter(description = "ID des zu aktualisierenden Benutzers", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Neue Daten für den Benutzer")
            @RequestBody UserUpdateDto userUpdateDto) {
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
    @Operation(summary = "Löscht einen Benutzer anhand der ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Benutzer erfolgreich gelöscht"),
            @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID des zu löschenden Benutzers", example = "1")
            @PathVariable Integer id) {
        UserDetailDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}