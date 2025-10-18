package com.wable.www.WableServer.api.quiz.controller;

import com.wable.www.WableServer.api.quiz.dto.response.QuizGetResponseDto;
import com.wable.www.WableServer.api.quiz.service.QuizQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="퀴즈 관련",description = "Quiz Api Document")
public class QuizController {
	private final QuizQueryService quizQueryService;

	@GetMapping("v1/quiz")
	@Operation(summary = "퀴즈 조회 API 입니다.",description = "Quiz Get")
	public ResponseEntity<ApiResponse<QuizGetResponseDto>> getQuiz(Principal principal) {
		return ApiResponse.success(GET_QUIZ_SUCCESS, quizQueryService.getQuiz());
	}
}
