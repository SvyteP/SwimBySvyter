package com.example.swimbysvyter.dto;

public record TrainingsGetDto(Long id, CustomersGetDto customersGetDTO,TrainingDto trainingDTO, boolean likeTrain, boolean completed) {
}
