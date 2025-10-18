package com.wable.www.WableServer.api.curation.service;

import com.wable.www.WableServer.api.curation.domain.Curation;
import com.wable.www.WableServer.api.curation.dto.response.CurationGetAllResponseDto;
import com.wable.www.WableServer.api.curation.repository.CurationRepository;
import com.wable.www.WableServer.common.util.TimeUtilCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurationQueryService {
	private final CurationRepository curationRepository;

	public List<CurationGetAllResponseDto> getCurationAll(Long memberId, Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, 15);
		Slice<Curation> curationList;

		if(cursor == -1) {
			curationList = curationRepository.findTop15ByOrderByCreatedAtDesc(pageRequest);
		} else {
			curationList = curationRepository.findCurationsByNextPage(cursor, pageRequest);
		}

		return curationList.stream()
				.map(oneCuration -> CurationGetAllResponseDto.of(
						oneCuration,
						TimeUtilCustom.refineTime(oneCuration.getCreatedAt())))
				.collect(Collectors.toList());
	}
}
