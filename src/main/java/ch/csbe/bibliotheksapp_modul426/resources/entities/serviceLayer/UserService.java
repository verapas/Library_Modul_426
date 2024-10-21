package ch.csbe.bibliotheksapp_modul426.resources.serviceLayer;

import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.entities.Loan;
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

    // Alle Benutzer abrufen
    public List<UserShowDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toUserShowDto)
                .collect(Collectors.toList());
    }

    // Benutzer nach ID finden
    public UserDetailDto findById(int id) {
        User user = userRepository.findById(id)
                .orElse(null);  // Gibt null zurück, wenn der Benutzer nicht gefunden wird
        return user != null ? toUserDetailDto(user) : null;
    }

    // Methode zum Erstellen eines neuen Benutzers
    public UserShowDto save(UserCreateDto userCreateDto) {
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());  // In einer echten Anwendung sollte das Passwort gehasht werden
        user.setRole(userCreateDto.getRole());

        User savedUser = userRepository.save(user);
        return toUserShowDto(savedUser);
    }

    // Methode zum Aktualisieren eines Benutzers
    public UserShowDto update(int id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));  // Benutzer suchen

        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());
        user.setPassword(userUpdateDto.getPassword());  // In einer echten Anwendung sollte das Passwort gehasht werden
        user.setRole(userUpdateDto.getRole());

        User updatedUser = userRepository.save(user);
        return toUserShowDto(updatedUser);
    }

    // Benutzer löschen
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    // Umwandlung von User in UserShowDto
    private UserShowDto toUserShowDto(User user) {
        UserShowDto userShowDto = new UserShowDto();
        userShowDto.setId(user.getId());
        userShowDto.setName(user.getName());
        userShowDto.setEmail(user.getEmail());
        userShowDto.setRole(user.getRole());
        return userShowDto;
    }

    // Umwandlung von User in UserDetailDto
    private UserDetailDto toUserDetailDto(User user) {
        UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.setId(user.getId());
        userDetailDto.setName(user.getName());
        userDetailDto.setEmail(user.getEmail());
        userDetailDto.setRole(user.getRole());

        List<Loan> loans = user.getLoans();
        List<UserDetailDto.LoanDto> loanDtos = loans.stream()
                .map(loan -> {
                    UserDetailDto.LoanDto loanDto = new UserDetailDto.LoanDto();
                    loanDto.setId(loan.getId());
                    loanDto.setBookId(loan.getBook().getId());
                    loanDto.setLoanDate(loan.getLoanDate());
                    loanDto.setReturnDate(loan.getReturnDate());
                    loanDto.setStatus(loan.getStatus());
                    return loanDto;
                })
                .collect(Collectors.toList());

        userDetailDto.setLoans(loanDtos);
        return userDetailDto;
    }
}