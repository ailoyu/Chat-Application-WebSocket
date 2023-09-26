package com.twinkle.ChatApplication.config;

import com.twinkle.ChatApplication.chat.ChatMessage;
import com.twinkle.ChatApplication.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j // for login
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    // Xử lý user thoát chat
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        // Lấy ra tên username
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            // thông báo cho user khác (user này đã thoát khỏi chat)
            // gửi thông báo thoát tới topic or channel or queue: /topic/public
            messageTemplate.convertAndSend("/topic/public", chatMessage);

        }
    }
}
