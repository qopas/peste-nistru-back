package org.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatMessageDTO {
    private Integer messageId;
    private Integer senderId;

    private String sender;

    private String messageText;

    private LocalDateTime timestamp;

    private String name;
    private String location;
}
