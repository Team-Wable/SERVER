package com.wable.www.WableServer.api.content.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ContentPostRequestDto(
        @NotBlank
		String contentTitle,

		String contentText
) {
}
