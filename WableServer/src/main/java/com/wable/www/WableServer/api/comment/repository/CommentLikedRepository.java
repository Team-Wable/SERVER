package com.wable.www.WableServer.api.comment.repository;

import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.comment.domain.CommentLiked;
import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikedRepository extends JpaRepository<CommentLiked, Long> {
    boolean existsByCommentAndMember(Comment comment, Member member);

    int countByComment(Comment comment);

    void deleteByCommentAndMember(Comment comment, Member member);

    void deleteByComment(Comment comment);

    List<CommentLiked> findAllByMemberId(Long memberId);
}
