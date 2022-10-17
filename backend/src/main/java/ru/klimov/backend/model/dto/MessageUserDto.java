package ru.klimov.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.klimov.backend.model.dto.marker.Exist;
import ru.klimov.backend.model.dto.marker.New;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class MessageUserDto {
    @NotNull(groups = {New.class, Exist.class}, message = "user.id обязательно для сообщения")
    private Long id;
    private String username;
}
