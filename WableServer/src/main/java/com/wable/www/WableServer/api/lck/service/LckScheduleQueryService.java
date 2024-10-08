package com.wable.www.WableServer.api.lck.service;

import com.wable.www.WableServer.api.lck.domain.LckSchedule;
import com.wable.www.WableServer.api.lck.dto.reponse.LckScheduleByDateDto;
import com.wable.www.WableServer.api.lck.dto.reponse.LckScheduleGetDto;
import com.wable.www.WableServer.api.lck.repository.LckScheduleRepository;
import com.wable.www.WableServer.common.util.TimeUtilCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LckScheduleQueryService {
	private final LckScheduleRepository lckScheduleRepository;

	public List<LckScheduleByDateDto> getLckSchedule() {
		LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endDate = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusDays(7);

		List<LckSchedule> lckSchedules = lckScheduleRepository.findAllByLckDateBetween(now, endDate);

		Map<String, List<LckScheduleGetDto>> groupedByDate = lckSchedules.stream()
				.map(lckSchedule -> LckScheduleGetDto.of(lckSchedule, TimeUtilCustom.refineTimeLckSchedule(lckSchedule.getLckDate())))
				.collect(Collectors.groupingBy(dto -> {
					LocalDateTime gameDateTime = LocalDateTime.parse(dto.gameDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
					return TimeUtilCustom.refineDateOnly(gameDateTime);
				}, TreeMap::new, Collectors.toList()));

		return groupedByDate.entrySet().stream()
				.map(entry -> LckScheduleByDateDto.of(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}
}
