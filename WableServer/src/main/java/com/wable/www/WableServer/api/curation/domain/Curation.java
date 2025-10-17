package com.wable.www.WableServer.api.curation.domain;

import com.wable.www.WableServer.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Curation extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long memberId;

	@Column(name = "curation_link")
	private String curationLink;

	@Builder
	public Curation(Long memberId, String curationLink) {
		this.memberId = memberId;
		this.curationLink = curationLink;
	}
}
