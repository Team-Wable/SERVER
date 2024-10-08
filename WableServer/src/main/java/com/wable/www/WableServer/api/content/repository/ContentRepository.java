package com.wable.www.WableServer.api.content.repository;

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

public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findContentById(Long contentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Content c WHERE c.isDeleted = true AND c.deleteAt < :currentDate")
    void deleteContentScheduledForDeletion(LocalDateTime currentDate);

    //게시물 전체 조회 관련

    List<Content> findAllByOrderByCreatedAtDesc();

    List<Content> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);

    //게시글 리스트 조회
    @Query("SELECT c FROM Content c WHERE c.id < :lastContentId ORDER BY c.createdAt DESC")
    Slice<Content> findContentsNextPage(Long lastContentId, PageRequest pageRequest);

    Slice<Content> findTop30ByOrderByCreatedAtDesc(PageRequest pageRequest);

    //멤버에 해당하는 게시글 리스트 조회
    @Query("SELECT c FROM Content c WHERE c.id < ?1 AND c.member.id = ?2 ORDER BY c.createdAt DESC")
    Slice<Content> findContentsByMemberNextPage(Long lastContentId, Long memberId, PageRequest pageRequest);

    Slice<Content> findContestsTop20ByMemberIdOrderByCreatedAtDesc(Long memberId,PageRequest pageRequest);

    default Content findContentByIdOrThrow(Long contentId) {
        return findContentById(contentId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_CONTENT.getMessage()));
    }

    List<Content> findContentByMember(Member member);

    @Query("SELECT c FROM Content c WHERE c.createdAt BETWEEN :startTime AND :endTime ORDER BY c.createdAt ASC")
    List<Content> findAllContentsBetweenDatesOrderedByAsc(LocalDateTime startTime, LocalDateTime endTime);
}