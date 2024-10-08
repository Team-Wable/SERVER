package com.wable.www.WableServer.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {
    /**
     * auth
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    SIGNIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    GET_NEW_TOKEN_SUCCESS(HttpStatus.OK,"토큰 재발급 성공"),
    WITHDRAWAL_SUCCESS(HttpStatus.OK,"유저 탈퇴 성공"),

    /**
     * content
     */
    CONTENT_LIKE_SUCCESS(HttpStatus.CREATED, "게시물 좋아요 성공"),
    GET_CONTENT_DETAIL_SUCCESS(HttpStatus.OK, "게시물 상세 조회 성공"),
    POST_CONTENT_SUCCESS(HttpStatus.CREATED,"게시글 작성 성공"),
    DELETE_CONTENT_SUCCESS(HttpStatus.OK,"게시글 삭제 성공"),
    CONTENT_UNLIKE_SUCCESS(HttpStatus.OK,"게시글 좋아요 취소 성공"),
    GET_CONTENT_ALL_SUCCESS(HttpStatus.OK, "게시물 리스트 조회 성공"),
    GET_MEMBER_CONTENT_SUCCESS(HttpStatus.OK,"유저에 해당하는 게시글 리스트 조회"),
    UPDATE_FCM_BADGE_SUCCESS(HttpStatus.OK, "유저의 배지값 수정 완료"),

    /**
     * comment
     */
    POST_COMMENT_SUCCESS(HttpStatus.CREATED,"답글 작성 성공"),
    DELETE_COMMENT_SUCCESS(HttpStatus.OK, "답글 삭제 성공"),
    COMMENT_LIKE_SUCCESS(HttpStatus.CREATED,"답글 좋아요 성공"),
    COMMENT_UNLIKE_SUCCESS(HttpStatus.OK,"답글 좋아요 취소 성공"),
    GET_COMMENT_ALL_SUCCESS(HttpStatus.OK,"게시물에 해당하는 답글 리스트 조회 성공"),
    GET_MEMBER_COMMENT_SECCESS(HttpStatus.OK,"멤버에 해당하는 답글 리스트 조회 성공"),

    /**
     * member
     */
    GET_MEMBER_DETAIL(HttpStatus.OK,"계정 정보 조회 성공"),
    GET_PROFILE_SUCCESS(HttpStatus.OK,"유저 프로필 조회 성공"),
    CLICK_MEMBER_GHOST_SUCCESS(HttpStatus.CREATED,"투명도 낮추기 성공"),
    PATCH_MEMBER_PROFILE(HttpStatus.OK, "프로필 수정 완료"),
    NICKNAME_CHECK_SUCCESS(HttpStatus.OK, "사용 가능한 닉네임 입니다."),
    NOTIFICATION_ALL_SUCCESS(HttpStatus.OK,"알림 전체 조회 성공"),
    INFO_NOTIFICATION_ALL_SUCCESS(HttpStatus.OK,"정보 알림 전체 조회 성공"),

    /**
     * notification
     */
    READ_NOTIFICATION_SUCCESS(HttpStatus.OK,"노티 체크 성공"),
    COUNT_NOTIFICATION_SUCCESS(HttpStatus.OK,"노티 개수 체크 완료"),
    POST_GAMEDONE_INFONOTIFICATION_SUCCESS(HttpStatus.CREATED,"게임 종료 정보 노티 생성 완료"),
    POST_GAMESTART_INFONOTIFICATION_SUCCESS(HttpStatus.CREATED,"게임 시작 정보 노티 생성 완료"),
    POST_WEEKDONE_INFONOTIFICATION_SUCCESS(HttpStatus.CREATED,"한 주 종료 정보 노티 생성 완료"),

    /**
     * report
     */
    REPORT_SLACK_ALARM_SUCCESS(HttpStatus.CREATED, "신고 관련 슬랙 알림 발생 성공"),
    /**
     * Lck Game
     */
    GET_LCK_RANKING_SUCCESS(HttpStatus.OK, "랭킹 목록 조회 성공"),
    GET_LCK_SCHEDULE_SUCCESS(HttpStatus.OK, "경기 일정 조회 성공"),
    GET_LCK_GAMETYPE_SUCCESS(HttpStatus.OK, "경기 종목 조회 성공")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}

