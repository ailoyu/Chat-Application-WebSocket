package com.twinkle.ChatApplication.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Thêm user (thông báo cho tất cả các user)
    @MessageMapping("/chat.addUser") // hàm này sẽ được gọi khi có một tin nhắn được gửi đến địa chỉ "/chat.addUser".
    @SendTo("/topic/public") // topic or queue muốn send// topic or queue muốn send
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor){

        // Add username in websocket session
        // cho phép server biết được người dùng nào đang tham gia trò chuyện
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    // Gửi tin nhắn
    @MessageMapping("/chat.sendMessage") // hàm được gọi khi có một tin nhắn được gửi đến địa chỉ "/chat.addUser".
    @SendTo("/topic/public") // topic or queue muốn send
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }
}
