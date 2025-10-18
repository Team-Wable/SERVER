package com.wable.www.WableServer.api.quiz.dto.request;

public record QuizGradeRequestDto(
		Long quizId,
		Boolean userAnswer,
		int quizTime
) {
}
