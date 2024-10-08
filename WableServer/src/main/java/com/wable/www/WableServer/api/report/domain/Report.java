package com.wable.www.WableServer.api.report.domain;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id")
    private Member reportTargetMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trigger_member_id")
    private Member reportTriggerMember;

    private Long reportTriggerId;

    private String reportReason;
}
