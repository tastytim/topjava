package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private  Map<Integer, User> userMap = new ConcurrentHashMap<>();
    private  AtomicInteger counter = new AtomicInteger(0);
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userMap.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userMap.put(user.getId(), user);
            return user;
        }
        return userMap.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }

    @Override
    public Collection<User> getAll() {
        log.info("getAll");
        return userMap.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userMap.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
