package moe.ksmz.rodentraid.sck.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.Auth.UserService;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messageBus;

    public PartyService(
            UserService userService,
            HuntManager huntManager,
            MiceManager miceManager,
            TrapManager trapManager,
            HuntRepository huntRepository,
            UserRepository userRepository,
            SimpMessagingTemplate messageBus) {
        this.userService = userService;
        this.huntManager = huntManager;
        this.miceManager = miceManager;
        this.trapManager = trapManager;
        this.huntRepository = huntRepository;
        this.userRepository = userRepository;
        this.messageBus = messageBus;
    }

    public void add(String room, Long userId) {
        log.info("[party] ID {}: attempting to join {}", userId, room);
        var currentRoom = rooms.getOrDefault(room, new HashSet<>());
        currentRoom.add(userId);
        log.info("[party] ID {}: added to {}", userId, room);

        rooms.put(room, currentRoom);
        log.info("[party] updated room {}", room);
    }

    public void leave(String room, Long userId) {
        if (!rooms.containsKey(room)) {
            return;
        }

        var currentRoom = rooms.get(room);
        currentRoom.remove(userId);
        rooms.put(room, currentRoom);
        log.info("[party] ID {}: left room {}", userId, room);
    }

    public void destroy(String room) {
        rooms.remove(room);
        log.info("[party] deleting room {}", room);
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
            var weight = mice.getRandWeight();
            freshHunt.setMouse(mice.getName());
            freshHunt.setWeight(weight);
            freshHunt.setCatchState(attempt);
            freshHunt.setUser(user);
            freshHunt.setCatchOutcome(CatchFormatter.getMessage(attempt, mice, weight));

            // successful hunt, award points and gold
            switch (attempt) {
                case POWER, LUCK -> {
                    user.increaseGold(mice.getGold());
                    user.increasePoints(mice.getPoints());
                }
            }

            user.decrementBait();

            huntRepository.save(freshHunt);
            userRepository.save(user);
            messageBus.convertAndSend("/topic/hunt/" + user.getId(), freshHunt);
        }

        return inRoom;
    }

    public List<Long> rawUsersIn(String room) {
        if (!rooms.containsKey(room)) {
            return new ArrayList<>();
        }

        return List.copyOf(rooms.get(room));
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
