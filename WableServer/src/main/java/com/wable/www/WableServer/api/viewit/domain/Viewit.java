package com.wable.www.WableServer.api.viewit.domain;

import com.wable.www.WableServer.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
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

	@JoinColumn(name = "viewit_image")
	private String viewitImage;

	@JoinColumn(name = "viewit_title")
	private String viewitTitle;

	@JoinColumn(name = "viewit_text")
	private String viewitText;

	@Column(name = "is_blind", columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isBlind;
}
