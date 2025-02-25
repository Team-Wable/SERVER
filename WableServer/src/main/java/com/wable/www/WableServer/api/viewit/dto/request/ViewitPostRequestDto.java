package com.wable.www.WableServer.api.viewit.dto.request;

public record ViewitPostRequestDto(
		String viewitImage,
		String viewitLink,
		String viewitTitle,
		String viewitText
) {
}
