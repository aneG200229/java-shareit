package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

public interface UserService {
    User findById(Long userId);

    User save(User user);

    void delete(Long userId);

    User update(User newUser);
}
