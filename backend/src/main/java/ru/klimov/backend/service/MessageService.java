package ru.klimov.backend.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.klimov.backend.model.dto.MessageDto;
import ru.klimov.backend.model.entity.Message;
import ru.klimov.backend.repository.MessageRepository;
import ru.klimov.backend.model.mapper.CoreMapper;

@Service
public class MessageService extends BaseServiceImpl<Message, Long> {
    private final String WEBSOCKET_MESSAGES = "/topic/messages";
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final CoreMapper mapper;
    public MessageService(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate, CoreMapper mapper) {
        super(messageRepository, Message.class);
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.mapper = mapper;
    }

    @Override
    public @NotNull Message create(@NotNull Message entity) {
        var message = super.create(entity);
        simpMessagingTemplate.convertAndSend(WEBSOCKET_MESSAGES, mapper.map(message, MessageDto.class));
        return message;
    }
}
