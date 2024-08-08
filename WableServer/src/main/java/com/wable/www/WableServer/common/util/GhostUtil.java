package com.wable.www.WableServer.common.util;

import com.wable.www.WableServer.common.exception.BadRequestException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RequiredArgsConstructor
public class GhostUtil {
    public static int refineGhost(int input) {
        if (input > 0) {
            throw new BadRequestException(ErrorStatus.GHOST_HIGHLIMIT.getMessage());
        } else if (input < -85) {
            return -85;
        }
        return input;
    }

    public static void isGhostMember(int memberGhost) {
        if(memberGhost<=-85) {
            throw new BadRequestException(ErrorStatus.GHOST_USER.getMessage());
        }
    }
}
