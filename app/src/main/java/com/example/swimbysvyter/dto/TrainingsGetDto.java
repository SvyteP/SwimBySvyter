package com.example.swimbysvyter.dto;

public record TrainingsGetDto(Long id, CustomersGetDto customersGetDTO, TrainingDto trainingsDTO, boolean likeTrain, boolean completed) {
}
