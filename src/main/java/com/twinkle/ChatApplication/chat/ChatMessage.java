package com.twinkle.ChatApplication.chat;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor // constructor full tham số
@NoArgsConstructor // constructor ko tham số
@Builder
public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;


}
