package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public User findById(Long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        return user;
    }


    @Override
    public User save(User user) {
        user.setId(idGenerator.incrementAndGet());
        users.put(user.getId(), user);
        log.info("Создан новый user: {}", user);
        return user;
    }

    @Override
    public void delete(Long userId) {
        if (users.remove(userId) == null) {
            throw new NotFoundException("Пользователь с id: " + userId + " не найден");
        }
        log.info("User удален id = {} ", userId);
    }

    @Override
    public User update(User newUser) {
        if (users.containsKey(newUser.getId())) {
            User user = users.get(newUser.getId());
            if (newUser.getName() != null) {
                user.setName(newUser.getName());
            }
            if (newUser.getEmail() != null) {
                user.setEmail(newUser.getEmail());
            }
            return user;
        }
        log.warn("Попытка обновить несуществующего пользователя с id={}", newUser.getId());
        throw new NotFoundException("User с id = " + newUser.getId() + " не найден");
    }

    @Override
    public User findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
