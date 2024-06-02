package ru.practicum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class StatsDtoOut {

    private String app;
    private String uri;
    private Long hits;
}