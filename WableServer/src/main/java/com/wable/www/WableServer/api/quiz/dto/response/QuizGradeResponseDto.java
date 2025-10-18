package com.wable.www.WableServer.api.quiz.dto.response;

public record QuizGradeResponseDto(
		Boolean quizResult,
		int userPercent,
		int continueNumber
) {
	public static QuizGradeResponseDto of(Boolean quizResult, int userPercent, int continueNumber) {
		return new QuizGradeResponseDto(
				quizResult,
				userPercent,
				continueNumber
		);
	}
}
