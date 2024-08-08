package com.wable.www.WableServer.api.member.dto.request;

public record MemberClickGhostRequestDto(
        String alarmTriggerType,
        Long targetMemberId,
        Long alarmTriggerId
) {
}
