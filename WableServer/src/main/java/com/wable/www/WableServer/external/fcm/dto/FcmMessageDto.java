package com.wable.www.WableServer.external.fcm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessageDto {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        @JsonProperty("notification")
        private NotificationDetails notificationDetails;
        private String token;
        private Data data;
        private int badge;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class NotificationDetails {
        private String title;
        private String body;
        private Integer badge;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {
        private String name;
        private String description;
        private String relateContentId;
    }
}