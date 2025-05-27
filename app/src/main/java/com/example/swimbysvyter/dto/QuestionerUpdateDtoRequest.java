package com.example.swimbysvyter.dto;

import com.example.swimbysvyter.entity.Questioner;

public record QuestionerUpdateDtoRequest(int lengthPool, String gender, int age, long complexityId,
                                         String levelTrain,
                                         int timeTrain, int countWeek, int countTrainOneWeek) {
    public QuestionerUpdateDtoRequest(Questioner questioner) {
        this(
                questioner.getLengthPool(),
                questioner.getGender(),
                questioner.getAge(),
                questioner.getComplexity().getId(),
                "",
                questioner.getTimeTrain(),
                questioner.getCountWeek(),
                questioner.getCountTrainOneWeek());
    }
}
