package com.wable.www.WableServer.api.curation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wable.www.WableServer.api.curation.domain.Curation;
import com.wable.www.WableServer.api.curation.dto.request.CurationPostRequestDto;
import com.wable.www.WableServer.api.curation.repository.CurationRepository;
import com.wable.www.WableServer.common.exception.BadRequestException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Transactional
public class CurationCommandService {

	private final CurationRepository curationRepository;

	@Value("${youtube.api-key}")
	private String youtubeApiKey;

	private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/videos";

	public void postCuration(CurationPostRequestDto curationPostRequestDto) {
		String link = curationPostRequestDto.curationLink();
		String title = null;
		String thumbnail = null;

		try {
			if (isYouTubeLink(link)) {
				String videoId = extractVideoId(link);
				if (videoId == null) {
					throw new BadRequestException("유효하지 않은 유튜브 링크입니다.");
				}

				String uri = UriComponentsBuilder.fromHttpUrl(YOUTUBE_API_URL)
						.queryParam("part", "snippet")
						.queryParam("id", videoId)
						.queryParam("key", youtubeApiKey)
						.toUriString();

				RestTemplate restTemplate = new RestTemplate();
				String response = restTemplate.getForObject(uri, String.class);

				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(response);
				JsonNode items = root.path("items");

				if (!items.isArray() || items.size() == 0) {
					throw new BadRequestException("유튜브 영상 정보를 찾을 수 없습니다.");
				}

				JsonNode snippet = items.get(0).path("snippet");
				title = snippet.path("title").asText();
				thumbnail = snippet.path("thumbnails").path("high").path("url").asText();

			} else {
				Document doc = Jsoup.connect(link)
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
								"AppleWebKit/537.36 (KHTML, like Gecko) " +
								"Chrome/120.0.0.0 Safari/537.36")
						.timeout(5000)
						.get();

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
			}

			// DB 저장
			curationRepository.save(
					Curation.builder()
							.curationLink(link)
							.curationTitle(title)
							.curationThumbnail(thumbnail)
							.build()
			);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException(ErrorStatus.CRAWLING_ERROR.getMessage());
		}
	}

	private boolean isYouTubeLink(String url) {
		return url.contains("youtube.com/watch?v=") || url.contains("youtu.be/");
	}

	private String extractVideoId(String url) {
		try {
			if (url.contains("v=")) {
				return url.substring(url.indexOf("v=") + 2).split("&")[0];
			} else if (url.contains("youtu.be/")) {
				return url.substring(url.indexOf("youtu.be/") + 9).split("\\?")[0];
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
