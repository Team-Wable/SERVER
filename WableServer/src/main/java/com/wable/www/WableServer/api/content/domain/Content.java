package com.wable.www.WableServer.api.content.domain;

import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "is_deleted = false")
public class Content extends BaseTimeEntity {

    private static final long CONTENT_RETENTION_PERIOD = 14L;   // 게시글 삭제 후 보유기간 14일로 설정

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String contentTitle;

//    @NotNull
    private String contentText;

//    @Column(columnDefinition = "NULL")
    private String contentImage;

    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<ContentLiked> contentLikeds = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

    private LocalDateTime deleteAt;

    public void setContentImage(String contentImageUrl) {
        this.contentImage = contentImageUrl ;}

    @Builder
    public Content(Member member, String contentTitle, String contentText) {
        this.member = member;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.contentImage = "";
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deleteAt = LocalDateTime.now().plusDays(CONTENT_RETENTION_PERIOD);
    }
}