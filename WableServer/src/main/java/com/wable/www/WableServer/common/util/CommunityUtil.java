package com.wable.www.WableServer.common.util;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CommunityUtil {
	private static final Map<String, Integer> TOTAL_MAP = Map.of(
			"T1", 80,
			"DK", 18,
			"GEN", 20,
			"KT", 10,
			"HLE", 22,
			"BFX", 8,
			"DNF", 8,
			"NS", 8,
			"DRX", 18,
			"BRO", 8
	);

	public static double calculatePercent(String communityName, int communityNumber) {
		int total = TOTAL_MAP.getOrDefault(communityName, 50);
		if (total == 0 || communityNumber == 0) return 0.0;
		if (communityNumber >= total) return 100.0;
		return Math.round(((double) communityNumber / total * 100.0) * 10) / 10.0;
	}
}
