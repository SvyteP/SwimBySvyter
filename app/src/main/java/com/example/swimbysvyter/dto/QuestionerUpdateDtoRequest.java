package com.example.swimbysvyter.dto;

public record QuestionerUpdateDtoRequest(int lengthPool, String gender, int age, long complexityId, String levelTrain,
                                         int timeTrain, int countWeek, int countTrainOneWeek) {
}
