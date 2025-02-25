package com.wable.www.WableServer.api.viewit.domain;

import com.wable.www.WableServer.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Viewit extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long memberId;

	@Column(name = "viewit_image")
	private String viewitImage;

	@Column(name = "viewit_link")
	private String viewitLink;

	@Column(name = "viewit_title")
	private String viewitTitle;

	@Column(name = "viewit_text")
	private String viewitText;

	@Column(name = "is_blind", columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isBlind;

	@Builder
	public Viewit(Long memberId, String viewitImage,String viewitLink, String viewitTitle, String viewitText) {
		this.memberId = memberId;
		this.viewitImage = viewitImage;
		this.viewitLink = viewitLink;
		this.viewitTitle = viewitTitle;
		this.viewitText = viewitText;
	}

	public void blindViewit() { this.isBlind = true;}
}
