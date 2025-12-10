package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item, Long userId);

    Item updateItem(Item item, Long userId);

    List<Item> getItemsByOwner(Long userId);

    List<Item> search(String text);

    void delete(Long userId, Long itemId);

    Item getById(Long itemId, Long userId);

}
