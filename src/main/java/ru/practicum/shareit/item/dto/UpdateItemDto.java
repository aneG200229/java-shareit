package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateItemDto {
    private Long id;
    private String name;
    private String description;
    @NotNull
    private Boolean available;
}
