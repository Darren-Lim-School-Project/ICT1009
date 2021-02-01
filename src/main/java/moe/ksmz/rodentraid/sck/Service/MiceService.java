package moe.ksmz.rodentraid.sck.Service;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.stereotype.Service;

@Service
public class MiceService implements MiceManager {
    private boolean loaded = false;

    @Override
    public List<Mice> loadEntries() {
        loaded = true;
        return null;
    }
}
