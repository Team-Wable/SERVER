package com.wable.www.WableServer.api.curation.service;

import com.wable.www.WableServer.api.curation.domain.Curation;
import com.wable.www.WableServer.api.curation.dto.request.CurationPostRequestDto;
import com.wable.www.WableServer.api.curation.repository.CurationRepository;
import com.wable.www.WableServer.common.exception.BadRequestException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CurationCommandService {
	private final CurationRepository curationRepository;

	public void postCuration(CurationPostRequestDto curationPostRequestDto) {
		String link = curationPostRequestDto.curationLink();

		// 기본값 (메타 태그 없을 경우 대비)
		String title = null;
		String thumbnail = null;

		try {
			Document doc = Jsoup.connect(link).get();

			Element ogTitle = doc.selectFirst("meta[property=og:title]");
			if (ogTitle != null) {
				title = ogTitle.attr("content");
			} else if (doc.title() != null) {
				title = doc.title();
			}

			Element ogImage = doc.selectFirst("meta[property=og:image]");
			if (ogImage != null) {
				thumbnail = ogImage.attr("content");
			}
		} catch (Exception e) {
			throw new BadRequestException(ErrorStatus.CRAWLING_ERROR.getMessage());
		}

		Curation curation = curationRepository.save(
				Curation.builder()
						.curationLink(link)
						.curationTitle(title)
						.curationThumbnail(thumbnail)
						.build()
		);
	}
}
