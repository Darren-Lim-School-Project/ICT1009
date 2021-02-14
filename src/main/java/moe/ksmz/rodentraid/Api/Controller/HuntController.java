package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hunt")
public class HuntController {
    private AuthStatus authStatus;
    private HuntRepository huntRepository;

    public HuntController(AuthStatus authStatus, HuntRepository huntRepository) {
        this.authStatus = authStatus;
        this.huntRepository = huntRepository;
    }

    @GetMapping("/newHunt")
    Hunt hunt() {
        var u = authStatus.getCurrentUser();

        var n = new Hunt();
        n.setMouse("Alchemist");
        n.setWeight(1L);
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
