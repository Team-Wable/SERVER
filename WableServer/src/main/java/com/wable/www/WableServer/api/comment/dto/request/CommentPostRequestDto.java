package com.wable.www.WableServer.api.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentPostRequestDto(
        @NotBlank String commentText,
        @NotBlank String notificationTriggerType
) {
}
