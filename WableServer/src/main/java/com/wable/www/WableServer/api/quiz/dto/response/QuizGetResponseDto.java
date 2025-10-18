package com.wable.www.WableServer.api.quiz.dto.response;

import com.wable.www.WableServer.api.quiz.domain.Quiz;

public record QuizGetResponseDto(
		Long quizId,
		String quizImage,
		String quizText,
		Boolean quizAnswer
) {
	public static QuizGetResponseDto of(Quiz quiz) {
		return new QuizGetResponseDto(
				quiz.getId(),
				quiz.getQuizImage(),
				quiz.getQuizText(),
				quiz.getQuizAnswer()
		);
	}
}
