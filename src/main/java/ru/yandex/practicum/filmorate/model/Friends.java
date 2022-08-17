package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Friends {
    private Long id;
    private Long userId;
    private Long friendsId;
    private Boolean confirming;
}
