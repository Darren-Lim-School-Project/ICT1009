package moe.ksmz.rodentraid.Api.Controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Auth.PartyStatus;
import moe.ksmz.rodentraid.Models.InsufficientBaitException;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.Response.PartyResponse;
import moe.ksmz.rodentraid.sck.Service.PartyOnCooldownException;
import moe.ksmz.rodentraid.sck.Service.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/party")
@Slf4j
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
        if (!partyStatus.inParty()) {
            return ResponseEntity.notFound().build();
        }

        var user = authStatus.getCurrentUser();
        var room = partyStatus.getRoom();
        var inRoom = partyService.rawUsersIn(room);
        if (!inRoom.contains(user.getId())) {
            log.info("ID: {} not in room {}, removing", user.getId(), room);
            partyStatus.clearRoom();
        }

        return ResponseEntity.ok(new PartyResponse(room, partyService.usersIn(room)));
    }

    @PostMapping("/join/{room}")
    ResponseEntity<List<User>> joinParty(@PathVariable String room) {
        var userId = authStatus.id();
        if (partyStatus.inParty()) {
            partyService.leave(partyStatus.getRoom(), userId);
        }

        partyService.add(room, authStatus.id());
        partyStatus.setRoom(room);
        messageBus.convertAndSend("/topic/join/room/" + room, partyService.usersIn(room));

        return ResponseEntity.ok(partyService.usersIn(room));
    }

    @PostMapping("/leave")
    ResponseEntity<?> leaveParty() {
        var room = partyStatus.getRoom();
        partyService.leave(room, authStatus.id());
        partyStatus.clearRoom();
        messageBus.convertAndSend("/topic/leave/room/" + room, partyService.usersIn(room));

        return ResponseEntity.ok(partyService.usersIn(room));
    }

    @PostMapping("/startHunt")
    ResponseEntity<?> startPartyHunt() {
        if (!partyStatus.inParty()) {
            return ResponseEntity.notFound().build();
        }

        var room = partyStatus.getRoom();
        List<User> users;
        try {
            users = partyService.huntTogetherIfPossible(room);
        } catch (PartyOnCooldownException exception) {
            return ResponseEntity.status(400)
                    .body(
                            Collections.singletonMap(
                                    "message",
                                    "Not all members can sound the horn: "
                                            + exception.users.stream()
                                                    .map(User::getName)
                                                    .collect(Collectors.joining(","))));
        } catch (InsufficientBaitException exception) {
            return ResponseEntity.status(400)
                    .body(
                            Collections.singletonMap(
                                    "message", "One or more members have insufficient bait"));
        }

        return ResponseEntity.ok(users);
    }
}
