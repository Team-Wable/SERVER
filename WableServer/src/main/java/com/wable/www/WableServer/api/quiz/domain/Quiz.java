package com.wable.www.WableServer.api.quiz.domain;

import com.wable.www.WableServer.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Quiz extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quiz_image")
	private String quizImage;

	@Column(name = "quiz_text")
	private String quizText;

	@Column(name = "quiz_answer")
	private Boolean quizAnswer;

	@Column(name = "quiz_date")
	private LocalDate quizDate;

	@Builder
	public Quiz(String quizImage, String quizText, Boolean quizAnswer, LocalDate quizDate) {
		this.quizImage = quizImage;
		this.quizText = quizText;
		this.quizAnswer = quizAnswer;
		this.quizDate = quizDate;
	}
}
