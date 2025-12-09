package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item addItem(Item item, Long userId) {
        User owner = userRepository.findById(userId);
        item.setOwner(owner);
        itemRepository.save(item);
        log.info("Предмет сохранен {}", item);
        return item;
    }

    @Override
    public Item updateItem(Item item, Long userId) {
        if (userId == null) {
            throw new InternalServerException("Userid не передан");
        }
        Item oldItem = itemRepository.findById(item.getId());
        if (!oldItem.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Предмет может обновить только его владелец");
        }
        itemRepository.update(item);
        log.info("Предмет обновлен {}", item);
        return item;
    }

    @Override
    public List<Item> getItemsByOwner(Long userId) {
        if (userRepository.findById(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        return itemRepository.findByOwnerId(userId);
    }

    @Override
    public List<Item> search(String text) {
        return itemRepository.search(text);
    }

    @Override
    public void delete(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId);
        userRepository.findById(userId);
        if (!item.getOwner().getId().equals(userId)) {
            throw new ConflictException("Предмет может удалить только его владелец");
        }
        itemRepository.delete(itemId);

    }

    @Override
    public Item getById(Long itemId, Long userId) {
        userRepository.findById(userId);
        return itemRepository.findById(itemId);
    }
}
