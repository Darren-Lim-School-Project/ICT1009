package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Auth.PartyStatus;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.Response.PartyResponse;
import moe.ksmz.rodentraid.sck.Service.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/party")
public class PartyController {
    private final SimpMessagingTemplate messageBus;
    private final PartyService partyService;
    private final AuthStatus authStatus;
    private final PartyStatus partyStatus;

    public PartyController(
            SimpMessagingTemplate messageBus,
            PartyService partyService,
            AuthStatus authStatus,
            PartyStatus partyStatus) {
        this.messageBus = messageBus;
        this.partyService = partyService;
        this.authStatus = authStatus;
        this.partyStatus = partyStatus;
    }

    @GetMapping("/room")
    ResponseEntity<PartyResponse> room() {
        if (partyStatus.inParty()) {
            var room = partyStatus.getRoom();

            return ResponseEntity.ok(new PartyResponse(room, partyService.usersIn(room)));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/join/{room}")
    ResponseEntity<List<User>> joinParty(@PathVariable String room) {
        partyService.add(room, authStatus.id());
        partyStatus.setRoom(room);
        messageBus.convertAndSend("/topic/room/" + room, partyService.usersIn(room));

        return ResponseEntity.ok(partyService.usersIn(room));
    }

    @GetMapping("/startHunt")
    ResponseEntity<?> startPartyHunt() {
        if (!partyStatus.inParty()) {
            return ResponseEntity.notFound().build();
        }

        var room = partyStatus.getRoom();
        var users = partyService.huntTogetherIfPossible(room);

        return ResponseEntity.ok(users);
    }
}
