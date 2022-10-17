package ru.klimov.backend.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.klimov.backend.model.dto.marker.Exist;
import ru.klimov.backend.model.dto.marker.New;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.time.LocalDateTime;
/**
 * DTO для класса Message
 */
@Data
public class MessageDto {
    @Null(groups = {New.class}, message = "Поле id не дложно передаваться при создании")
    @NotNull(groups = {Exist.class}, message = "Не задано поле id")
    private Long id;
    @NotNull(groups = {New.class, Exist.class}, message = "Не задано поле text")
    @Length(groups = {New.class, Exist.class}, min = 1, max = 256, message = "Поле username должно быть от 1 до 256 символов")
    private String text;
    @NotNull(groups = {New.class}, message = "Не задано поле time")
    private Instant time;
    @Valid
    @NotNull(groups = {New.class, Exist.class}, message = "Не задано поле user")
    private MessageUserDto user;
}
