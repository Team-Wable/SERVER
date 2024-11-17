package com.wable.www.WableServer.api.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentPostRequestDtoVer2(
		@NotBlank String commentText,
		Long parentCommentId,
		Long parentCommentWriterId
) {
}
