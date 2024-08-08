package com.wable.www.WableServer.external.slack.service;

import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.report.dto.ReportSlackRequestDto;
import com.wable.www.WableServer.common.util.TextUtil;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {
    private final MemberRepository memberRepository;

    @Value(value = "${slack.token}")
    String slackToken;

    public void sendSlackMessage(Long totalMember, String channel) {
        String message = totalMember.toString() + "번째 예스비가 새롭게 탄생했어요.";
        try {
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text(message)
                    .build();
            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendReportSlackMessage(Long triggerMemberId, ReportSlackRequestDto reportSlackRequestDto) {
        String triggerMemberNickname = memberRepository.findMemberByIdOrThrow(triggerMemberId).getNickname();
        String relateText = TextUtil.cuttingText(30, reportSlackRequestDto.relateText());
        String message = triggerMemberNickname + " 님이 " + reportSlackRequestDto.reportTargetNickname() + " 님을 신고했습니다."
                + "\n" + "관련 내용 : " + relateText;
        try {
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel("#wable-report-slack")
                    .text(message)
                    .build();
            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}