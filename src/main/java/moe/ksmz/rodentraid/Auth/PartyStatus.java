package moe.ksmz.rodentraid.Auth;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class PartyStatus implements Serializable {
    @Serial private static final long serialVersionUID = 42069L;

    private String room = null;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean inParty() {
        return room != null;
    }

    public void clearRoom() {
        this.room = null;
    }
}
