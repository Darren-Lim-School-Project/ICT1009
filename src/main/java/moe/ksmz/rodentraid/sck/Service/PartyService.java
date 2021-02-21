package moe.ksmz.rodentraid.sck.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.Auth.UserService;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PartyService {
    private final Map<String, Set<Long>> rooms = new ConcurrentHashMap<>();

    private final UserService userService;
    private final HuntManager huntManager;
    private final MiceManager miceManager;
    private final TrapManager trapManager;
    private final HuntRepository huntRepository;

    public PartyService(
            UserService userService,
            HuntManager huntManager,
            MiceManager miceManager,
            TrapManager trapManager,
            HuntRepository huntRepository) {
        this.userService = userService;
        this.huntManager = huntManager;
        this.miceManager = miceManager;
        this.trapManager = trapManager;
        this.huntRepository = huntRepository;
    }

    public void add(String room, Long userId) {
        var currentRoom = rooms.getOrDefault(room, new HashSet<>());
        currentRoom.add(userId);

        rooms.put(room, currentRoom);
    }

    public void leave(String room, Long userId) {
        if (!rooms.containsKey(room)) {
            return;
        }

        var currentRoom = rooms.get(room);
        currentRoom.remove(userId);
        rooms.put(room, currentRoom);
    }

    public void destroy(String room) {
        rooms.remove(room);
    }

    public List<User> huntTogetherIfPossible(String room)
            throws PartyOnCooldownException, NoMiceInLocationException {
        var inRoom = usersIn(room);
        var usersOnCooldown = cannotHuntYet(inRoom);
        if (usersOnCooldown.isPresent()) {
            throw new PartyOnCooldownException(usersOnCooldown.get());
        }

        var location = inRoom.get(0).getLocation();
        var randMice = miceManager.getRandomMiceForLocation(location);
        if (randMice.isEmpty()) {
            throw new NoMiceInLocationException(location);
        }

        var mice = randMice.get();
        for (var user : inRoom) {
            var weapon = trapManager.getWeaponFor(user);
            var attempt = weapon.attemptCatch(mice);

            var freshHunt = new Hunt();
            freshHunt.setMouse(mice.getName());
            freshHunt.setWeight(mice.getRandWeight());
            freshHunt.setCatchState(attempt);
            freshHunt.setUser(user);

            huntRepository.save(freshHunt);
        }

        return inRoom;
    }

    public List<User> usersIn(String room) {
        if (!rooms.containsKey(room)) {
            return new ArrayList<>();
        }

        return hydrate(List.copyOf(rooms.get(room)));
    }

    private List<User> hydrate(List<Long> userIDs) {
        return userService.getUsers(userIDs);
    }

    private Optional<List<User>> cannotHuntYet(List<User> users) {
        // get all users (if any) that can't hunt
        var filtered =
                users.stream()
                        .filter(user -> !huntManager.canHuntAgain(user.getId()))
                        .collect(Collectors.toList());

        return filtered.size() > 0 ? Optional.of(filtered) : Optional.empty();
    }
}
