package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hunt")
public class HuntController {
    private final AuthStatus authStatus;
    private final HuntRepository huntRepository;
    private final MiceManager miceService;
    private final HuntManager huntService;

    public HuntController(
            AuthStatus authStatus,
            HuntRepository huntRepository,
            MiceManager miceService,
            HuntManager huntService) {
        this.authStatus = authStatus;
        this.huntRepository = huntRepository;
        this.miceService = miceService;
        this.huntService = huntService;
    }

    @GetMapping({"", "/"})
    ResponseEntity<List<Hunt>> all() {
        return ResponseEntity.of(huntService.getAllHunts(authStatus.id()));
    }

    @GetMapping("/newHunt/{location}")
    ResponseEntity<Hunt> hunt(@PathVariable String location) {
        var u = authStatus.getCurrentUser();
        var randMice = miceService.getRandomMiceForLocation(location);

        if (randMice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var mice = randMice.get();
        var huntAttempt = new Hunt();
        huntAttempt.setMouse(mice.getName());
        huntAttempt.setWeight(mice.getRandWeight());
        huntAttempt.setUser(u);
        huntRepository.save(huntAttempt);

        return ResponseEntity.ok(huntAttempt);
    }

    @GetMapping("/latest")
    ResponseEntity<Hunt> last() {
        return ResponseEntity.of(huntService.getLatestHunt(authStatus.id()));
    }
}
