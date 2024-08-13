package com.wable.www.WableServer.api.lck.service;

import com.wable.www.WableServer.api.lck.dto.reponse.LckGameTypeGetDto;
import com.wable.www.WableServer.api.lck.repository.LckGameTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LckGameTypeQueryService {
	private final LckGameTypeRepository lckGameTypeRepository;

	public LckGameTypeGetDto getLckGameType() {
		String gameType = lckGameTypeRepository.findLckGameTypeById(1L).getLckGameType();
		return LckGameTypeGetDto.of(gameType);
	}
}
