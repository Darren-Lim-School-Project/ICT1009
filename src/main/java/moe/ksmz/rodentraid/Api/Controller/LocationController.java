package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.MiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final MiceService miceService;

    public LocationController(MiceService miceService) {
        this.miceService = miceService;
    }

    @GetMapping("/")
    List<Mice> locationsForMice() {
        return miceService.all();
    }

    @GetMapping("/{name}")
    List<Mice> locationsForMice(@PathVariable String name) {
        return miceService.allMiceForLocation(name);
    }
}
