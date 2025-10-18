package com.wable.www.WableServer.api.curation.service;

import com.wable.www.WableServer.api.curation.domain.Curation;
import com.wable.www.WableServer.api.curation.dto.request.CurationPostRequestDto;
import com.wable.www.WableServer.api.curation.repository.CurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CurationCommandService {
	private final CurationRepository curationRepository;

	public void postCuration(CurationPostRequestDto curationPostRequestDto) {
		Curation curation = curationRepository.save(Curation.builder()
				.curationLink(curationPostRequestDto.curationLink())
				.build());
	}
}
