package com.wable.www.WableServer.api.member.dto.request;

public record MemberProfilePatchRequestDto (
        String nickname,
        Boolean is_alarm_allowed,
        String member_intro,
        String profile_url
){
}
