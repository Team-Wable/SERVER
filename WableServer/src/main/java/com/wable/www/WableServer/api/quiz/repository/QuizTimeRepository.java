package com.wable.www.WableServer.api.quiz.repository;

import com.wable.www.WableServer.api.quiz.domain.QuizTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizTimeRepository extends JpaRepository<QuizTime,Long> {
	List<QuizTime> findAllByQuizIdOrderByQuizTimeAsc(Long quizId);
}
