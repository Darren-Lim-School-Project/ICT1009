package moe.ksmz.rodentraid.Api.Controller;

import java.util.Collections;
import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.InsufficientCreditsException;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.sck.Domain.Base;
import moe.ksmz.rodentraid.sck.Domain.Cat;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    private final AuthStatus authStatus;
    private final UserRepository userRepository;
    private final TrapManager trapManager;

    public ShopController(
            AuthStatus authStatus, UserRepository userRepository, TrapManager trapManager) {
        this.authStatus = authStatus;
        this.userRepository = userRepository;
        this.trapManager = trapManager;
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

    @GetMapping("/trap")
    List<Cat> allTraps() {
        return trapManager.allTraps();
    }

    @GetMapping("/base")
    List<Base> allBases() {
        return trapManager.allBases();
    }

    @PostMapping("/trap/{name}")
    ResponseEntity<?> buyTrap(@PathVariable String name) {
        var user = authStatus.getCurrentUser();
        var newTrap = trapManager.getCat(name);
        if (newTrap.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            user.buyItem(newTrap.get().getPower() * 100);
        } catch (InsufficientCreditsException exception) {
            return ResponseEntity.status(400)
                    .body(Collections.singletonMap("message", "Insufficient Credits"));
        }

        user.setTrap(newTrap.get().getName());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/base/{name}")
    ResponseEntity<?> buyBase(@PathVariable String name) {
        var user = authStatus.getCurrentUser();
        var newBase = trapManager.getBase(name);
        if (newBase.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            user.buyItem(newBase.get().getPower() * 100);
        } catch (InsufficientCreditsException exception) {
            return ResponseEntity.status(400)
                    .body(Collections.singletonMap("message", "Insufficient Credits"));
        }

        user.setBase(newBase.get().getName());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
