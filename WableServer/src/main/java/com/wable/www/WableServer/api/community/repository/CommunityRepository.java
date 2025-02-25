package com.wable.www.WableServer.api.community.repository;

import com.wable.www.WableServer.api.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
	Community getCommunityByCommunityName(String communityName);
}
