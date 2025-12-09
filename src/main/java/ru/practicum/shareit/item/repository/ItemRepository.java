package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item save(Item item);

    Item findById(Long id);

    Item update(Item item);

    List<Item> findByOwnerId(Long id);

    List<Item> search(String text);

    void delete(Long id);
}
