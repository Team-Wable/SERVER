package com.wable.www.WableServer.api.comment.service;

import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.comment.dto.response.*;
import com.wable.www.WableServer.api.comment.repository.CommentLikedRepository;
import com.wable.www.WableServer.api.comment.repository.CommentRepository;
import com.wable.www.WableServer.api.content.repository.ContentRepository;
import com.wable.www.WableServer.api.ghost.repository.GhostRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.common.util.GhostUtil;
import com.wable.www.WableServer.common.util.TimeUtilCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final GhostRepository ghostRepository;
    private final CommentLikedRepository commentLikedRepository;
    private final ContentRepository contentRepository;

    private final int COMMENT_DEFAULT_PAGE_SIZE = 15;

    public List<CommentAllResponseDto> getCommentAll(Long memberId, Long contentId) {
        contentRepository.findContentByIdOrThrow(contentId);

        List<Comment> commentList = commentRepository.findCommentsByContentIdOrderByCreatedAtAsc(contentId);

        return commentList.stream()
                .map( oneComment -> CommentAllResponseDto.of(
                        oneComment.getId(),
                        memberRepository.findMemberByIdOrThrow(oneComment.getMember().getId()),
                        checkGhost(memberId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        checkLikedComment(memberId,oneComment.getId()),
                        TimeUtilCustom.refineTime(oneComment.getCreatedAt()),
                        likedNumber(oneComment.getId()),
                        oneComment.getCommentText()))
                .collect(Collectors.toList());
    }

    public List<CommentAllByMemberResponseDto> getMemberComment(Long principalId, Long memberId){
        memberRepository.findMemberByIdOrThrow(memberId);
        List<Comment> commentList = commentRepository.findCommentsByMemberIdOrderByCreatedAtDesc(memberId);

        return commentList.stream()
                .map( oneComment -> CommentAllByMemberResponseDto.of(
                        memberRepository.findMemberByIdOrThrow(memberId),
                        checkLikedComment(principalId, oneComment.getId()),
                        checkGhost(principalId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        likedNumber(oneComment.getId()),
                        oneComment)
                ).collect(Collectors.toList());
    }

    public List<CommentAllResponseDtoVer2> getCommentAllPagination(Long memberId, Long contentId, Long cursor) {
        contentRepository.findContentByIdOrThrow(contentId);
        PageRequest pageRequest = PageRequest.of(0, COMMENT_DEFAULT_PAGE_SIZE);
        Slice<Comment> commentList;

        commentList = commentRepository.findCommentsByContentNextPage(cursor, contentId, pageRequest);

        return commentList.stream()
                .map(oneComment -> CommentAllResponseDtoVer2.of(
                        oneComment.getId(),
                        memberRepository.findMemberByIdOrThrow(oneComment.getMember().getId()),
                        checkGhost(memberId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        checkLikedComment(memberId,oneComment.getId()),
                        TimeUtilCustom.refineTime(oneComment.getCreatedAt()),
                        likedNumber(oneComment.getId()),
                        oneComment.getCommentText()))
                .collect(Collectors.toList());
    }

    public List<CommentAllResponseDtoVer3> getCommentAllWithImage(Long memberId, Long contentId, Long cursor) {
        contentRepository.findContentByIdOrThrow(contentId);
        PageRequest pageRequest = PageRequest.of(0, COMMENT_DEFAULT_PAGE_SIZE);
        Slice<Comment> commentList;

        commentList = commentRepository.findCommentsByContentNextPage(cursor, contentId, pageRequest);

        return commentList.stream()
                .map(oneComment -> CommentAllResponseDtoVer3.of(
                        oneComment.getId(),
                        memberRepository.findMemberByIdOrThrow(oneComment.getMember().getId()),
                        checkGhost(memberId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        checkLikedComment(memberId,oneComment.getId()),
                        TimeUtilCustom.refineTime(oneComment.getCreatedAt()),
                        likedNumber(oneComment.getId()),
                        oneComment.getCommentText(),
                        oneComment.getCommentImage()))
                .collect(Collectors.toList());
    }

    public List<CommentAllByMemberResponseDto> getMemberCommentPagination(Long principalId, Long memberId, Long cursor) {
        memberRepository.findMemberByIdOrThrow(memberId);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Slice<Comment> commentList;

        if (cursor==-1) {
            commentList = commentRepository.findCommentsTop15ByMemberIdOrderByCreatedAtDesc(memberId, pageRequest);
        } else {
            commentList = commentRepository.findCommentsByMemberNextPage(cursor, memberId, pageRequest);
        }

        return commentList.stream()
                .map(oneComment -> CommentAllByMemberResponseDto.of(
                        memberRepository.findMemberByIdOrThrow(memberId),
                        checkLikedComment(principalId, oneComment.getId()),
                        checkGhost(principalId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        likedNumber(oneComment.getId()),
                        oneComment)
                ).collect(Collectors.toList());
    }

    public List<CommentAllByMemberResponseDtoVer2> getCommentAllByMemberWithImage(Long principalId, Long memberId, Long cursor) {
        memberRepository.findMemberByIdOrThrow(memberId);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Slice<Comment> commentList;

        if (cursor==-1) {
            commentList = commentRepository.findCommentsTop15ByMemberIdOrderByCreatedAtDesc(memberId, pageRequest);
        } else {
            commentList = commentRepository.findCommentsByMemberNextPage(cursor, memberId, pageRequest);
        }

        return commentList.stream()
                .map(oneComment -> CommentAllByMemberResponseDtoVer2.of(
                        memberRepository.findMemberByIdOrThrow(memberId),
                        checkLikedComment(principalId, oneComment.getId()),
                        checkGhost(principalId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        likedNumber(oneComment.getId()),
                        oneComment)
                ).collect(Collectors.toList());
    }

    public List<CommentAllByMemberResponseDtoVer3> getCommentAllByMemberWithBlind(Long principalId, Long memberId, Long cursor) {
        memberRepository.findMemberByIdOrThrow(memberId);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Slice<Comment> commentList;

        if (cursor==-1) {
            commentList = commentRepository.findCommentsTop15ByMemberIdOrderByCreatedAtDesc(memberId, pageRequest);
        } else {
            commentList = commentRepository.findCommentsByMemberNextPage(cursor, memberId, pageRequest);
        }

        return commentList.stream()
                .map(oneComment -> CommentAllByMemberResponseDtoVer3.of(
                        memberRepository.findMemberByIdOrThrow(memberId),
                        checkLikedComment(principalId, oneComment.getId()),
                        checkGhost(principalId, oneComment.getId()),
                        checkMemberGhost(oneComment.getId()),
                        likedNumber(oneComment.getId()),
                        oneComment)
                ).collect(Collectors.toList());
    }

    public List<CommentAllResponseDtoVer4> getCommentsWithHierarchy(Long memberId, Long contentId, Long cursor) {
        PageRequest pageRequest = PageRequest.of(0, COMMENT_DEFAULT_PAGE_SIZE);
        Slice<Comment> parentComments = commentRepository.findParentCommentsWithPaginationAfterCursor(cursor, contentId, pageRequest);

        // 결과 리스트 초기화
        List<CommentAllResponseDtoVer4> result = new ArrayList<>();

        for (Comment parent : parentComments) {
            // 대댓글 조회 및 변환
            List<Comment> childComments = commentRepository.findChildComments(parent.getId());

            List<CommentAllResponseDtoVer4> childDtos = childComments.stream()
                    .map(child -> CommentAllResponseDtoVer4.of(
                            child.getId(),
                            memberRepository.findMemberByIdOrThrow(child.getMember().getId()),
                            checkGhost(memberId, child.getId()),
                            checkMemberGhost(child.getId()),
                            checkLikedComment(memberId, child.getId()),
                            TimeUtilCustom.refineTime(child.getCreatedAt()),
                            likedNumber(child.getId()),
                            child.getCommentText(),
                            child.getCommentImage(),
                            child.getParentCommentId(),
                            child.isBlind(),
                            null // 대댓글의 대댓글은 없으므로 null로 설정
                    ))
                    .collect(Collectors.toList());

            // 부모 댓글 DTO 변환 (대댓글 포함)
            CommentAllResponseDtoVer4 parentDto = CommentAllResponseDtoVer4.of(
                    parent.getId(),
                    memberRepository.findMemberByIdOrThrow(parent.getMember().getId()),
                    checkGhost(memberId, parent.getId()),
                    checkMemberGhost(parent.getId()),
                    checkLikedComment(memberId, parent.getId()),
                    TimeUtilCustom.refineTime(parent.getCreatedAt()),
                    likedNumber(parent.getId()),
                    parent.getCommentText(),
                    parent.getCommentImage(),
                    parent.getParentCommentId(),
                    parent.isBlind(),
                    childDtos // 대댓글 추가
            );

            result.add(parentDto);
        }
        return result;
    }

    private boolean checkGhost(Long usingMemberId, Long commentId) {
        Member writerMember = commentRepository.findCommentByIdOrThrow(commentId).getMember();
        return ghostRepository.existsByGhostTargetMemberIdAndGhostTriggerMemberId(writerMember.getId(), usingMemberId);
    }

    private boolean checkLikedComment(Long usingMemberId, Long commentId) {
        Member member = memberRepository.findMemberByIdOrThrow(usingMemberId);
        Comment comment = commentRepository.findCommentByIdOrThrow(commentId);
        return commentLikedRepository.existsByCommentAndMember(comment, member);
    }

    private int checkMemberGhost(Long commentId) {
        Member member = commentRepository.findCommentByIdOrThrow(commentId).getMember();
        return GhostUtil.refineGhost(member.getMemberGhost());
    }

    private int likedNumber(Long commentId) {
        Comment comment = commentRepository.findCommentByIdOrThrow(commentId);
        return commentLikedRepository.countByComment(comment);
    }
}
