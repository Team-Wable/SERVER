package com.wable.www.WableServer.api.member.dto.request;

import java.util.List;

public record MemberWithdrawalPatchRequestDto(
        List<String> deleted_reason
) {
}
