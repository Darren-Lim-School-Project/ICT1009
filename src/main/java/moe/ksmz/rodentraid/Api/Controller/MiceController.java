package moe.ksmz.rodentraid.Api.Controller;

import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.MiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mice")
public class MiceController {
    private final MiceService miceService;

    public MiceController(MiceService miceService) {
        this.miceService = miceService;
    }

    @GetMapping("/{name}")
    ResponseEntity<Mice> findMice(@PathVariable String name) {
        var mouse = miceService.getMouse(name);

        return ResponseEntity.of(mouse);
    }
}
