package moe.ksmz.rodentraid.Api.Websocket;

import moe.ksmz.rodentraid.Request.ChatRequest;
import moe.ksmz.rodentraid.Response.ChatResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat/{partyId}")
    @SendTo("/topic/chat/{partyId}")
    ChatResponse send(@DestinationVariable String partyId, ChatRequest message) {
        return ChatResponse.fromRequest(message);
    }
}
