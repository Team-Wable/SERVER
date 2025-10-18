package com.wable.www.WableServer.common.util;

import lombok.RequiredArgsConstructor;
import com.wable.www.WableServer.common.exception.UnAuthorizedException;
import com.wable.www.WableServer.common.response.ErrorStatus;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MemberUtil {
    private static final Map<BigDecimal, Integer> LEVEL_MAP = new LinkedHashMap<>();

    static {
        LEVEL_MAP.put(new BigDecimal("8"), 1);
        LEVEL_MAP.put(new BigDecimal("23"), 2);
        LEVEL_MAP.put(new BigDecimal("47"), 3);
        LEVEL_MAP.put(new BigDecimal("82"), 4);
        LEVEL_MAP.put(new BigDecimal("130"), 5);
        LEVEL_MAP.put(new BigDecimal("194"), 6);
        LEVEL_MAP.put(new BigDecimal("277"), 7);
        LEVEL_MAP.put(new BigDecimal("381"), 8);
        LEVEL_MAP.put(new BigDecimal("508"), 9);
        LEVEL_MAP.put(new BigDecimal("662"), 10);
        LEVEL_MAP.put(new BigDecimal("816"), 11);
        LEVEL_MAP.put(new BigDecimal("970"), 12);
        LEVEL_MAP.put(new BigDecimal("1124"), 13);
        LEVEL_MAP.put(new BigDecimal("1278"), 14);
        LEVEL_MAP.put(new BigDecimal("1463"), 15);
        LEVEL_MAP.put(new BigDecimal("1683"), 16);
        LEVEL_MAP.put(new BigDecimal("1942"), 17);
        LEVEL_MAP.put(new BigDecimal("2244"), 18);
        LEVEL_MAP.put(new BigDecimal("2593"), 19);
        LEVEL_MAP.put(new BigDecimal("3005"), 20);
        LEVEL_MAP.put(new BigDecimal("3492"), 21);
        LEVEL_MAP.put(new BigDecimal("4066"), 22);
        LEVEL_MAP.put(new BigDecimal("4743"), 23);
        LEVEL_MAP.put(new BigDecimal("5542"), 24);
        LEVEL_MAP.put(new BigDecimal("6485"), 25);
        LEVEL_MAP.put(new BigDecimal("7598"), 26);
        LEVEL_MAP.put(new BigDecimal("8911"), 27);
        LEVEL_MAP.put(new BigDecimal("10460"), 28);
        LEVEL_MAP.put(new BigDecimal("12288"), 29);
        LEVEL_MAP.put(new BigDecimal("14444"), 30);
        LEVEL_MAP.put(new BigDecimal("16988"), 31);
        LEVEL_MAP.put(new BigDecimal("19987"), 32);
        LEVEL_MAP.put(new BigDecimal("23526"), 33);
        LEVEL_MAP.put(new BigDecimal("27702"), 34);
        LEVEL_MAP.put(new BigDecimal("32630"), 35);
        LEVEL_MAP.put(new BigDecimal("38445"), 36);
        LEVEL_MAP.put(new BigDecimal("45304"), 37);
        LEVEL_MAP.put(new BigDecimal("53387"), 38);
        LEVEL_MAP.put(new BigDecimal("62905"), 39);
        LEVEL_MAP.put(new BigDecimal("74040"), 40);

    }
    public static Long getMemberId(Principal principal) {
        if (principal == null) {
            throw new UnAuthorizedException(ErrorStatus.INVALID_MEMBER.getMessage());
        }
        return Long.valueOf(principal.getName());
    }

    public static int refineMemberExpToLevel(BigDecimal memberExp) {
        if (memberExp == null) return 0;

        for (Map.Entry<BigDecimal, Integer> entry : LEVEL_MAP.entrySet()) {
            if (memberExp.compareTo(entry.getKey()) <= 0) {
                return entry.getValue();
            }
        }
        return 41;
    }
}
