package ru.klimov.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klimov.backend.model.dto.MessageDto;
import ru.klimov.backend.model.entity.Message;
import ru.klimov.backend.service.MessageService;
import ru.klimov.backend.model.mapper.CoreMapper;

/**
 * Контроллер для работы с сообщениями
 */
@RestController
@RequestMapping("/api/message")
public class MessageController extends BaseController<Message, Long, MessageDto> {

    public MessageController(MessageService messageService, CoreMapper mapper) {
        super(messageService, mapper, Message.class, MessageDto.class);
    }
}
