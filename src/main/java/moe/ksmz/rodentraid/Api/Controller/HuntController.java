package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import java.util.Random;

import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.sck.Service.MiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hunt")
public class HuntController {
    private AuthStatus authStatus;
    private HuntRepository huntRepository;
    private MiceService miceService;

    public HuntController(AuthStatus authStatus, HuntRepository huntRepository, MiceService miceService) {
        this.authStatus = authStatus;
        this.huntRepository = huntRepository;
        this.miceService = miceService;
    }

    @GetMapping("/newHunt/{location}")
    Hunt hunt(@PathVariable String location) {
        var u = authStatus.getCurrentUser();
        var mice = miceService.allMiceForLocation(location);
        var rand = new Random();
        var randMouse = mice.get(rand.nextInt(mice.size()));

        var n = new Hunt();
        n.setMouse(randMouse.getName());
        n.setWeight(randMouse.getWeight());
        n.setUser(u);
        huntRepository.save(n);

        return n;
    }

    @GetMapping("/latest")
    ResponseEntity<Hunt> last() {
        return ResponseEntity.of(
                huntRepository.findFirstByUserIdOrderByCreatedAtDesc(
                        authStatus.getCurrentUser().getId()));
    }

    @GetMapping({"", "/"})
    ResponseEntity<List<Hunt>> all() {
        return ResponseEntity.of(
                huntRepository.findAllByUserIdOrderByCreatedAtDesc(
                        authStatus.getCurrentUser().getId()));
    }
}
