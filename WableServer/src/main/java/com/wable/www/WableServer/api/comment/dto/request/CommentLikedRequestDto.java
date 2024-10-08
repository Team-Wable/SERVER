package com.wable.www.WableServer.api.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentLikedRequestDto(
        @NotBlank String notificationTriggerType,
        @NotBlank String notificationText
) {
}
