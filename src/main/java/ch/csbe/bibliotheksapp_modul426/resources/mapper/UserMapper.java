package ch.csbe.bibliotheksapp_modul426.resources.mapper;

import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserCreateDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserDetailDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserShowDto;
import ch.csbe.bibliotheksapp_modul426.resources.dto.User.UserUpdateDto;
import ch.csbe.bibliotheksapp_modul426.resources.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper-Klasse für die Umwandlung von User-Entitäten in DTOs und umgekehrt.
 * Verwendet MapStruct für das Mapping.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Umwandlung von User in UserShowDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    UserShowDto toUserShowDto(User user);

    // Umwandlung von User in UserDetailDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    UserDetailDto toUserDetailDto(User user);

    // Erstellung eines neuen Benutzers
    User toUserEntity(UserCreateDto userCreateDto);

    // Aktualisierung eines Benutzers
    void updateUserEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
