package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final MiceManager miceManager;

    public LocationController(MiceManager miceManager) {
        this.miceManager = miceManager;
    }

    @GetMapping("/")
    List<Mice> locationsForMice() {
        return miceManager.all();
    }

    @GetMapping("/{name}")
    List<Mice> locationsForMice(@PathVariable String name) {
        return miceManager.allMiceForLocation(name);
    }
}
