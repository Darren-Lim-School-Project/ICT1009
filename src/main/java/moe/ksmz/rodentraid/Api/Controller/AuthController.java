package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Auth.UserService;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.Request.Auth.Login;
import moe.ksmz.rodentraid.Request.Auth.Register;
import moe.ksmz.rodentraid.Response.Auth.UserResponse;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthStatus authStatus;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TrapManager trapManager;

    @GetMapping("/me")
    UserResponse me() {
        var refreshed = userRepository.findById(authStatus.id()).get();
        authStatus.setCurrentUser(refreshed);
        var user = authStatus.getCurrentUser();

        return UserResponse.fromUser(user, trapManager.getWeaponFor(user));
    }

    @GetMapping({"/forceLogin", "/forceLogin/{id}"})
    ResponseEntity<?> forceLogin(@PathVariable Optional<Long> id) {
        var user = userRepository.findById(id.orElse(1L));

        if (user.isEmpty()) {
            log.error("failed to force login id: {}", 1);

            return ResponseEntity.badRequest().build();
        }

        authStatus.setCurrentUser(user.get());

        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .header(HttpHeaders.LOCATION, "/api/auth/me")
                .build();
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@RequestBody Login login) {
        var user = userService.attemptLogin(login.getEmail(), login.getPassword());

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        authStatus.setCurrentUser(user.get());

        return ResponseEntity.ok(
                UserResponse.fromUser(user.get(), trapManager.getWeaponFor(user.get())));
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(HttpServletRequest request) {
        var session = request.getSession();
        session.invalidate();

        return ResponseEntity.status(200).build();
    }

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody Register register) {
        var user =
                userService.register(
                        register.getName(), register.getEmail(), register.getPassword());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        authStatus.setCurrentUser(user.get());

        return ResponseEntity.ok(
                UserResponse.fromUser(user.get(), trapManager.getWeaponFor(user.get())));
    }

    @GetMapping("/scores")
    ResponseEntity<List<User>> topScores() {
        return ResponseEntity.ok(userRepository.findAllByOrderByPointsDesc());
    }
}
