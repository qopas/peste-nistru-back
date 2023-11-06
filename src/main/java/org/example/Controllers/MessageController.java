package org.example.Controllers;

import org.example.Dto.MessageDTO;
import org.example.chatRoom.Message;
import org.example.deepL.TranslatorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;
import org.springframework.messaging.handler.annotation.Payload;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping()
public class MessageController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageService messageService;

    @Autowired
    public MessageController(SimpMessageSendingOperations messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    // Endpoint for sending a message to a chat room

    @GetMapping("api/messages/{roomId}/{language}")
    public List<MessageDTO> getMessagesForRoom(@PathVariable Integer roomId, @PathVariable String language) throws Exception {
        // Retrieve messages for the specified chat room
        TranslatorClass translatorClass = new TranslatorClass();
        List<MessageDTO> messageDTOS = messageService.getMessagesForRoom(roomId);
        for (MessageDTO m: messageDTOS){
            m.setMessageText(translatorClass.translateText(m.getMessageText(), language).getText());
        }
        return messageDTOS;
    }
}