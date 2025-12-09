package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;



public interface UserRepository {
    User findById(Long userId);
    User save(User user);
    void delete(Long userId);
    User update(User newUser);
    User findByEmail(String email);
}
