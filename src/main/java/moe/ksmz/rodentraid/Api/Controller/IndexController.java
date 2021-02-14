package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.MiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {
    private MiceService miceService;

    public IndexController(MiceService miceService) {
        this.miceService = miceService;
    }

    @GetMapping("/locations")
    List<Mice> locationsForMice() {
        return miceService.all();
    }

    @GetMapping("/locations/{name}")
    List<Mice> locationsForMice(@PathVariable String name) {
        return miceService.allMiceForLocation(name);
    }
}
