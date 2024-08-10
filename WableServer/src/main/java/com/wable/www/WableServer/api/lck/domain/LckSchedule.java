package com.wable.www.WableServer.api.lck.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class LckSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime lckDate;

	@Column(name = "team_a_name")
	private String teamAName;

	@Column(name = "team_a_score")
	private int teamAScore;

	@Column(name = "team_b_name")
	private String teamBName;

	@Column(name = "team_b_score")
	private int teamBScore;

	private GameState gameState;
}
