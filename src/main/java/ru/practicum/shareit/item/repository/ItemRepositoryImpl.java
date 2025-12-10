package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Item save(Item item) {
        item.setId(idGenerator.incrementAndGet());
        items.put(item.getId(), item);
        log.info("Предмет создан {}", item);
        return item;
    }

    @Override
    public Item findById(Long id) {
        Item item = items.get(id);
        if (item == null) {
            throw new NotFoundException("Предмет с id = " + id + " не найден");
        }
        return item;

    }

    @Override
    public Item update(Item item) {
        if (items.containsKey(item.getId())) {
            Item updateItem = items.get(item.getId());
            if (item.getName() != null) {
                updateItem.setName(item.getName());
            }
            if (item.getDescription() != null) {
                updateItem.setDescription(item.getDescription());
            }
            if (item.getAvailable() != null) {
                updateItem.setAvailable(item.getAvailable());
            }
            return updateItem;
        }
        throw new NotFoundException("Предмет с id = " + item.getId() + " не найден");
    }

    @Override
    public List<Item> findByOwnerId(Long id) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        String lowerText = text.toLowerCase();
        return items.values().stream()
                .filter(Item::getAvailable)
                .filter(item ->
                        (item.getName() != null && item.getName().toLowerCase().contains(lowerText)) ||
                                (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerText))
                )
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        items.remove(id);
    }
}
