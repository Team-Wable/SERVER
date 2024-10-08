package com.wable.www.WableServer.api.member.repository;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.common.exception.NotFoundException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberById(Long memberId);
    Optional<Member> findMemberByRefreshToken(String refreshToken);

    default Member findMemberByIdOrThrow(Long memberId) {
        return findMemberById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER.getMessage()));
    }

    default Member findByRefreshTokenOrThrow(String refreshToken) {
        return findMemberByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER.getMessage()));
    }

    boolean existsBySocialId(String socialId);

    Optional<Member> findBySocialId(String socialId);

    boolean existsByNickname(String nickname);

    @Transactional
    @Modifying
    @Query("UPDATE Member m " +
            "SET m.isAlarmAllowed=false , m.memberEmail= '',m.memberGhost=0,m.memberIntro='',m.refreshToken='', m.socialId='',m.socialNickname='',m.socialPlatform='WITHDRAW' " +
            "WHERE m.isDeleted=true AND m.deleteAt < :currentDate")
    void deleteMemberScheduledForDeletion(LocalDateTime currentDate);

    Member findMemberBySocialId(String socialId);

    @Query("SELECT m FROM Member m WHERE m.isDeleted = false")
    List<Member> findAllActiveMembers();
}