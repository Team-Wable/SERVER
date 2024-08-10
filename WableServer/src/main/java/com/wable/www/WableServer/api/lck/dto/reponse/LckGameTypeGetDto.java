package com.wable.www.WableServer.api.lck.dto.reponse;

public record LckGameTypeGetDto(
		String lckGameType
) {
	public static LckGameTypeGetDto of(String lckGameType) {
		return new LckGameTypeGetDto(
				lckGameType
		);
	}
}
