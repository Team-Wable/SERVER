package com.wable.www.WableServer.api.lck.repository;

import com.wable.www.WableServer.api.lck.domain.LckGameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LckGameTypeRepository extends JpaRepository<LckGameType, Long> {
	LckGameType findLckGameTypeById(Long Id);
}
