package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Service.Contracts.LocationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    private final LocationManager locationManager;
    private final AuthStatus authStatus;
    private final UserRepository userRepository;

    public LocationController(
            LocationManager locationManager, AuthStatus authStatus, UserRepository userRepository) {
        this.locationManager = locationManager;
        this.authStatus = authStatus;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/"})
    List<Location> all() {
        return locationManager.all();
    }

    @PostMapping("/{location}")
    ResponseEntity<?> travelTo(@PathVariable String location) {
        var locationExists = locationManager.find(location);
        if (locationExists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var user = authStatus.getCurrentUser();
        user.setLocation(locationExists.get().getName());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
