package moe.ksmz.rodentraid.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import moe.ksmz.rodentraid.Request.ChatRequest;

@Getter
@AllArgsConstructor
public class ChatResponse {
    private final String username;
    private final String message;

    public static ChatResponse fromRequest(ChatRequest chatRequest) {
        return new ChatResponse(chatRequest.getUsername(), chatRequest.getMessage());
    }
}
