package moe.ksmz.rodentraid.Api.Controller;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mice")
public class MiceController {
    private final MiceManager miceManager;

    public MiceController(MiceManager miceManager) {
        this.miceManager = miceManager;
    }

    @GetMapping({"", "/"})
    ResponseEntity<List<Mice>> all() {
        return ResponseEntity.ok(miceManager.all());
    }

    @GetMapping("/{name}")
    ResponseEntity<Mice> findMice(@PathVariable String name) {
        var mouse = miceManager.getMouse(name);

        return ResponseEntity.of(mouse);
    }
}
