package com.wable.www.WableServer.api.comment.repository;

import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.common.exception.NotFoundException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByContent(Content content);

    List<Comment> findCommentsByContentId(Long contentId);

    Optional<Comment> findCommentById(Long commentId);

    List<Comment> findCommentsByMemberIdOrderByCreatedAtDesc(Long memberId);    // 페이지네이션 적용 후 지우기

    List<Comment> findCommentsByContentIdOrderByCreatedAtAsc(Long contentId);    // 페이지네이션 적용 후 지우기

    //게시물에 해당하는 답글 리스트 조회
    @Query("SELECT c FROM Comment c WHERE c.id > ?1 AND c.content.id = ?2 ORDER BY c.createdAt")
    Slice<Comment> findCommentsByContentNextPage(Long lastCommentId, Long contentId, PageRequest pageRequest);

    //멤버에 해당하는 답글 리스트 조회
    @Query("SELECT c FROM Comment c WHERE c.id < ?1 AND c.member.id = ?2  ORDER BY c.createdAt desc")
    Slice<Comment> findCommentsByMemberNextPage(Long lastCommentId, Long memberId,PageRequest pageRequest);

    Slice<Comment> findCommentsTop15ByMemberIdOrderByCreatedAtDesc(Long memberId, PageRequest pageRequest);

    default Comment findCommentByIdOrThrow(Long commentId) {
        return findCommentById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_COMMENT.getMessage()));
    }

    void deleteCommentsByMemberId(Long memberId);

    List<Comment> findAllByMember(Member member);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.isDeleted = true AND c.deleteAt < :currentDate")
    void deleteCommentScheduledForDeletion(LocalDateTime currentDate);
}