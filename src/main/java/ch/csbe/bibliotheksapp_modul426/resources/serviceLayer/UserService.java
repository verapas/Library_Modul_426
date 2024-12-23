package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.mapper.UserMapper;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.*;
import ch.csbe.bibliotheksapp_modul426.resources.entities.User;
import ch.csbe.bibliotheksapp_modul426.resources.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Alle Benutzer abrufen
    public List<UserShowDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserShowDto)
                .collect(Collectors.toList());
    }

    // Benutzer nach ID finden
    public UserDetailDto findById(int id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDetailDto)
                .orElse(null);  // Gibt null zurück, wenn der Benutzer nicht gefunden wird
    }

    // Benutzer registrieren (prüft auf doppelte E-Mail)
    public UserShowDto registerUser(UserCreateDto userCreateDto) {
        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-Mail existiert bereits.");
        }
        User user = userMapper.toUserEntity(userCreateDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserShowDto(savedUser);
    }

    // Benutzer anmelden (optional)
    public UserShowDto authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Ungültige Anmeldedaten");
        }
        return userMapper.toUserShowDto(user);
    }

    // Benutzer speichern
    public UserShowDto save(UserCreateDto userCreateDto) {
        User user = userMapper.toUserEntity(userCreateDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserShowDto(savedUser);
    }

    // Methode zum Aktualisieren eines Benutzers
    public UserShowDto update(int id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUserEntity(userUpdateDto, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserShowDto(updatedUser);
    }

    // Benutzer löschen
    public void delete(int id) {
        userRepository.deleteById(id);
    }


}
