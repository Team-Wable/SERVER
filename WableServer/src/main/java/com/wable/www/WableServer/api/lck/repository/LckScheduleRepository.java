package com.wable.www.WableServer.api.lck.repository;

import com.wable.www.WableServer.api.lck.domain.LckSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LckScheduleRepository extends JpaRepository<LckSchedule, Long> {
	List<LckSchedule> findAllByLckDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
