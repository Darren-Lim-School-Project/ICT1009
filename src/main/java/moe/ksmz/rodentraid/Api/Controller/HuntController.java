package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Response.HuntAttempt;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hunt")
public class HuntController {
    private final AuthStatus authStatus;
    private final UserRepository userRepository;
    private final HuntRepository huntRepository;
    private final MiceManager miceService;
    private final HuntManager huntService;
    private final TrapManager trapManager;

    public HuntController(
            AuthStatus authStatus,
            UserRepository userRepository,
            HuntRepository huntRepository,
            MiceManager miceService,
            HuntManager huntService,
            TrapManager trapManager) {
        this.authStatus = authStatus;
        this.userRepository = userRepository;
        this.huntRepository = huntRepository;
        this.miceService = miceService;
        this.huntService = huntService;
        this.trapManager = trapManager;
    }

    @GetMapping({"", "/"})
    ResponseEntity<List<Hunt>> all() {
        return ResponseEntity.of(huntService.getAllHunts(authStatus.id()));
    }

    @GetMapping("/newHunt/{location}")
    ResponseEntity<HuntAttempt> hunt(@PathVariable String location) {
        var u = authStatus.getCurrentUser();
        var randMice = miceService.getRandomMiceForLocation(location);

        // no mice at this location
        if (randMice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!huntService.canHuntAgain(u.getId())) {
            var latest = huntService.getLatestHunt(u.getId()).get();
            return ResponseEntity.ok(HuntAttempt.tooEarly(latest));
        }

        var mice = randMice.get();
        var weapon = trapManager.getWeaponFor(u);
        var attempt = weapon.attemptCatch(mice);

        var freshHunt = new Hunt();
        freshHunt.setMouse(mice.getName());
        freshHunt.setWeight(mice.getRandWeight());
        freshHunt.setCatchState(attempt);
        freshHunt.setUser(u);

        huntRepository.save(freshHunt);

        switch (attempt) {
                // successful hunt, award points and gold
            case POWER, LUCK -> {
                u.increaseGold(mice.getGold());
                u.increasePoints(mice.getPoints());
                userRepository.save(u);
            }
        }

        return ResponseEntity.ok(HuntAttempt.success(freshHunt, mice));
    }

    @GetMapping("/latest")
    ResponseEntity<Hunt> last() {
        return ResponseEntity.of(huntService.getLatestHunt(authStatus.id()));
    }
}
