package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Response.HuntAttempt;
import moe.ksmz.rodentraid.sck.Service.CatchFormatter;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hunt")
public class HuntController {
    private final AuthStatus authStatus;
    private final UserRepository userRepository;
    private final HuntRepository huntRepository;
    private final MiceManager miceService;
    private final HuntManager huntManager;
    private final TrapManager trapManager;
    private final SimpMessagingTemplate messageBus;

    public HuntController(
            AuthStatus authStatus,
            UserRepository userRepository,
            HuntRepository huntRepository,
            MiceManager miceService,
            HuntManager huntService,
            TrapManager trapManager,
            SimpMessagingTemplate messageBus) {
        this.authStatus = authStatus;
        this.userRepository = userRepository;
        this.huntRepository = huntRepository;
        this.miceService = miceService;
        this.huntManager = huntService;
        this.trapManager = trapManager;
        this.messageBus = messageBus;
    }

    @GetMapping({"", "/"})
    ResponseEntity<List<Hunt>> all() {
        return ResponseEntity.of(huntManager.getAllHunts(authStatus.id()));
    }

    @PostMapping("/newHunt")
    ResponseEntity<HuntAttempt> hunt() {
        var user = authStatus.getCurrentUser();
        var location = user.getLocation();
        var randMice = miceService.getRandomMiceForLocation(location);

        // no mice at this location
        if (randMice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!huntManager.canHuntAgain(user.getId())) {
            var latest = huntManager.getLatestHunt(user.getId()).get();
            return ResponseEntity.ok(HuntAttempt.tooEarly(latest));
        }

        var mice = randMice.get();
        var weapon = trapManager.getWeaponFor(user);
        var attempt = weapon.attemptCatch(mice);

        var freshHunt = new Hunt();
        var weightOfCatch = mice.getRandWeight();
        freshHunt.setMouse(mice.getName());
        freshHunt.setWeight(weightOfCatch);
        freshHunt.setCatchState(attempt);
        freshHunt.setUser(user);
        freshHunt.setCatchOutcome(CatchFormatter.getMessage(attempt, mice, weightOfCatch));
        huntRepository.save(freshHunt);

        switch (attempt) {
                // successful hunt, award points and gold
            case POWER, LUCK -> {
                user.increaseGold(mice.getGold());
                user.increasePoints(mice.getPoints());
            }
        }

        userRepository.save(user);
        messageBus.convertAndSend("/topic/hunt/" + user.getId(), freshHunt);

        return ResponseEntity.ok(HuntAttempt.success(freshHunt, mice));
    }

    @GetMapping("/latest")
    ResponseEntity<Hunt> last() {
        return ResponseEntity.of(huntManager.getLatestHunt(authStatus.id()));
    }
}
