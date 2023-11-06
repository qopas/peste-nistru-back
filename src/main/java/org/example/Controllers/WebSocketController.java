package org.example.Controllers;

import com.deepl.api.DeepLException;
import org.example.Dto.MessageDTO;
import org.example.chatRoom.Message;
import org.example.deepL.TranslatorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
@Controller
public class WebSocketController {
    @Autowired
    private MessageService messageService;
    private final static TranslatorClass translatorClass;

    static {
        try {
            translatorClass = new TranslatorClass();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage/{roomID}")
    @SendTo("/topic/{roomID}")
    public MessageDTO sendMessage(@Payload MessagePayload message) throws DeepLException, InterruptedException {
        Message saved = messageService.saveMessage(message);
        String s = translatorClass.translateText(message.getMessage(), message.getLanguage()).getText();
        System.out.println("Received and processed message: " + message.getMessage());
        return new MessageDTO(
                saved.getId(),
                message.getSenderId(),
                null,
                s,
                LocalDateTime.now()
        );
    }
}
