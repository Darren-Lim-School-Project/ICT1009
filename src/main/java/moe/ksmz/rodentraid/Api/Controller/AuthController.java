package moe.ksmz.rodentraid.Api.Controller;

import lombok.RequiredArgsConstructor;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Response.Auth.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthStatus authStatus;
    private final UserRepository userRepository;

    @GetMapping("/me")
    UserResponse me() {
        var user = authStatus.getCurrentUser().get();

        return new UserResponse(user.getName(), user.getEmail(), user.getCurrentRank());
    }

    @GetMapping("/login")
    String login() {
        authStatus.setCurrentUser(userRepository.findById(1L).get());
        return "ok";
    }
}
