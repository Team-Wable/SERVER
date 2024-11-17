package com.wable.www.WableServer.api.content.repository;

import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.content.domain.ContentLiked;
import com.wable.www.WableServer.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContentLikedRepository extends JpaRepository<ContentLiked,Long> {
    boolean existsByContentAndMember(Content content, Member member);

    int countByContent(Content content);

    void deleteByContentAndMember(Content content, Member member);

    List<ContentLiked> findAllByMemberId(Long memberId);
}
