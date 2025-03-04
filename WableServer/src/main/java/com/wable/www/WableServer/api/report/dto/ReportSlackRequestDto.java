package com.wable.www.WableServer.api.report.dto;

public record ReportSlackRequestDto(
        String reportTargetNickname,
        String relateText
) {
}
