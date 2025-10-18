package com.wable.www.WableServer.api.quiz.repository;

import com.wable.www.WableServer.api.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
	Quiz findQuizByQuizDate(LocalDate quizDate);

	Quiz findQuizById(Long quizId);
}
