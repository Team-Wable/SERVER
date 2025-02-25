package com.wable.www.WableServer.common.util;

import lombok.RequiredArgsConstructor;
import com.wable.www.WableServer.common.exception.UnAuthorizedException;
import com.wable.www.WableServer.common.response.ErrorStatus;

import java.security.Principal;

@RequiredArgsConstructor
public class MemberUtil {
    public static Long getMemberId(Principal principal) {
        if (principal == null) {
            throw new UnAuthorizedException(ErrorStatus.INVALID_MEMBER.getMessage());
        }
        return Long.valueOf(principal.getName());
    }

    public static int refineMemberExpToLevel(double memberExp) {
        if (memberExp >= 0 && memberExp <= 8) {
            return 1;
        } else if (memberExp >= 9 && memberExp <= 23) {
            return 2;
        } else if (memberExp > 23 && memberExp <= 47) {
            return 3;
        } else if (memberExp > 47 && memberExp <= 82) {
            return 4;
        } else if (memberExp > 82 && memberExp <= 130) {
            return 5;
        } else if (memberExp > 130 && memberExp <= 194) {
            return 6;
        } else if (memberExp > 194 && memberExp <= 277) {
            return 7;
        } else if (memberExp > 277 && memberExp <= 381) {
            return 8;
        } else if (memberExp > 381 && memberExp <= 508) {
            return 9;
        } else if (memberExp > 508 && memberExp <= 662) {
            return 10;
        } else if (memberExp > 662 && memberExp <= 816) {
            return 11;
        } else if (memberExp > 816 && memberExp <= 970) {
            return 12;
        } else if (memberExp > 970 && memberExp <= 1124) {
            return 13;
        } else if (memberExp > 1124 && memberExp <= 1278) {
            return 14;
        } else if (memberExp > 1278 && memberExp <= 1463) {
            return 15;
        } else if (memberExp > 1463 && memberExp <= 1683) {
            return 16;
        } else if (memberExp > 1683 && memberExp <= 1942) {
            return 17;
        } else if (memberExp > 1942 && memberExp <= 2244) {
            return 18;
        } else if (memberExp > 2244 && memberExp <= 2593) {
            return 19;
        } else {
            return -1;
        }
    }
}
