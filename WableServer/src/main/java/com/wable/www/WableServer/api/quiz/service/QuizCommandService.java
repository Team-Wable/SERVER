package com.wable.www.WableServer.api.quiz.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.quiz.domain.Quiz;
import com.wable.www.WableServer.api.quiz.domain.QuizTime;
import com.wable.www.WableServer.api.quiz.dto.request.QuizGradeRequestDto;
import com.wable.www.WableServer.api.quiz.dto.response.QuizGradeResponseDto;
import com.wable.www.WableServer.api.quiz.repository.QuizRepository;
import com.wable.www.WableServer.api.quiz.repository.QuizTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizCommandService {
	private final QuizRepository quizRepository;
	private  final QuizTimeRepository quizTimeRepository;
	private final MemberRepository memberRepository;

	public QuizGradeResponseDto gradeQuiz(Long memberId, QuizGradeRequestDto quizGradeRequestDto) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);
		LocalDate today = LocalDate.now();
		LocalDate memberQuizDate = member.getMemberQuizDate();
		int memberQuizCount = member.getMemberQuizCount();
		Quiz quiz = quizRepository.findQuizById(quizGradeRequestDto.quizId());
		Boolean quizResult = quiz.getQuizAnswer()==quizGradeRequestDto.userAnswer();

		QuizTime newQuizTime = quizTimeRepository.save(QuizTime.builder()
				.quizId(quizGradeRequestDto.quizId())
				.quizTime(quizGradeRequestDto.quizTime())
				.build());

		List<QuizTime> quizTimes = quizTimeRepository.findAllByQuizIdOrderByQuizTimeAsc(quizGradeRequestDto.quizId());
		int rank = 1;
		for(int i = 0; i < quizTimes.size(); i++) {
			if(quizTimes.get(i).getId().equals(newQuizTime.getId())){
				rank = i + 1;
				break;
			}
		}
		double percent = ((double) rank / quizTimes.size()) * 100;
		int memberPercent = (int) Math.floor(percent);

		if(quizResult){
			member.increaseExpQuizRight();
		}else {
			member.increaseExpQuizWrong();
		}

		if(compareDate(memberQuizDate,today)){
			member.updateMemberQuizDate(today);
			member.increaseQuizCount();
			return QuizGradeResponseDto.of(quizResult,memberPercent,memberQuizCount + 1);
		}else{
			member.updateMemberQuizDate(today);
			member.updateQuizCount();
			return QuizGradeResponseDto.of(quizResult,memberPercent,1);
		}


	}
	public boolean compareDate(LocalDate date1, LocalDate date2) {
		if(date1==null||date2==null) {
			return false;
		}
		int diff = Math.abs(Period.between(date1,date2).getDays());
		return diff == 1;
	}
}
