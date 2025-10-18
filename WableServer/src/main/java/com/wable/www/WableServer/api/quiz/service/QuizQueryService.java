package com.wable.www.WableServer.api.quiz.service;

import com.wable.www.WableServer.api.quiz.dto.response.QuizGetResponseDto;
import com.wable.www.WableServer.api.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizQueryService {
	private final QuizRepository quizRepository;

	public QuizGetResponseDto getQuiz() {
		LocalDate today = LocalDate.now();
		return QuizGetResponseDto.of(quizRepository.findQuizByQuizDate(today));
	}
}
