package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User findById(Long userId) {
        return repository.findById(userId);
    }

    @Override
    public User save(User user) {
        if (repository.findByEmail(user.getEmail()) == null) {
            return repository.save(user);
        } else {
            throw new ConflictException("Email уже используется");
        }

    }

    @Override
    public void delete(Long userId) {
        repository.delete(userId);
    }

    @Override
    public User update(User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("id должен быть введен");
        }
        if (newUser.getEmail() != null) {
            User existingUser = repository.findByEmail(newUser.getEmail());
            if (existingUser != null && !existingUser.getId().equals(newUser.getId())) {
                throw new ConflictException("Email занят другим пользователем");
            }
        }
        return repository.update(newUser);
    }

}
