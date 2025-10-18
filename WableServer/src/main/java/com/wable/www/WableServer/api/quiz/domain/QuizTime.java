package com.wable.www.WableServer.api.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class QuizTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quiz_id")
	private Long quizId;

	@Column(name = "quiz_time")
	private int quizTime;

	@Builder
	public QuizTime(Long quizId, int quizTime) {
		this.quizId = quizId;
		this.quizTime = quizTime;
	}
}
