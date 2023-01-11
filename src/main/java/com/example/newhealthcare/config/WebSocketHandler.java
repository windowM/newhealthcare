package com.example.newhealthcare.config;

import com.example.newhealthcare.dto.ChatDTO;
import com.example.newhealthcare.dto.ChatRoomDTO;
import com.example.newhealthcare.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    //메세지를 json형식을 통해서 웹소켓을 통해 서버로 보내면,
//Handler는 이를받아 ObjectMapper를 통해서 해당 json 데이터를 chatMessage.class에 맞게 파싱하여 ChatMessage 객체로 변환하고,
//이 json 데이터에 들어있는 roomId를 이용해서 해당 채팅방을 찾아 handlerAction() 이라는 메서드를 실행시킬 것이다.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);
        ChatDTO chatMessage = objectMapper.readValue(payload, ChatDTO.class);

        ChatRoomDTO chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}