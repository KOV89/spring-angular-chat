package ru.klimov.backend.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.klimov.backend.model.entity.Role;
import ru.klimov.backend.model.dto.marker.Exist;
import ru.klimov.backend.model.dto.marker.New;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

/**
 * DTO для класса User
 */
@Data
public class UserDto {
    @Null(groups = {New.class}, message = "Поле id не дложно передаваться при создании")
    @NotNull(groups = {Exist.class}, message = "Не задано поле id")
    private Long id;
    @NotNull(groups = {New.class, Exist.class}, message = "Не задано поле username")
    @Length(groups = {New.class, Exist.class}, min = 4, max = 16, message = "Поле username должно быть от 4 до 16 символов")
    private String username;
    @NotNull(groups = {New.class, Exist.class}, message = "Не задано поле password")
    @Length(groups = {New.class, Exist.class}, min = 4, max = 16, message = "Поле password должно быть от 4 до 16 символов")
    private String password;
    @NotNull(groups = {Exist.class}, message = "Не задано поле enabled")
    private boolean enabled = true;
    @NotNull(groups = {Exist.class}, message = "Не заданы роли")
    @Size(groups = {Exist.class}, min = 1, message = "Не заданы роли")
    private Set<Role> roles = Collections.singleton(Role.ROLE_USER);
}
