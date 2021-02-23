package moe.ksmz.rodentraid.Api.Controller;

import java.util.Collections;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.InsufficientCreditsException;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    private final AuthStatus authStatus;
    private final UserRepository userRepository;

    public ShopController(AuthStatus authStatus, UserRepository userRepository) {
        this.authStatus = authStatus;
        this.userRepository = userRepository;
    }

    @PostMapping("/{quantity}")
    ResponseEntity<?> buy(@PathVariable Long quantity) {
        var user = authStatus.getCurrentUser();
        try {
            user.buyBait(quantity);
        } catch (InsufficientCreditsException exception) {
            return ResponseEntity.status(400)
                    .body(Collections.singletonMap("message", "Insufficient Credits"));
        }

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
