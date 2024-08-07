package ru.practicum.event.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LocationDto {

    private Long id;

    @NotNull(message = "The latitude field cannot be null.")
    private Double lat;

    @NotNull(message = "The longitude field cannot be null.")
    private Double lon;
}
