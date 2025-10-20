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

	@Column(name = "curation_link")
	private String curationLink;

	@Column(name = "curation_title")
	private String curationTitle;

	@Column(name = "curation_thumbnail", length = 1000)
	private String curationThumbnail;

	@Builder
	public Curation(Long memberId, String curationLink, String curationTitle, String curationThumbnail) {
		this.curationLink = curationLink;
		this.curationTitle = curationTitle;
		this.curationThumbnail = curationThumbnail;
	}
}
