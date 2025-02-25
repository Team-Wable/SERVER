package com.wable.www.WableServer.api.community.repository;

import com.wable.www.WableServer.api.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
	Community getCommunityByCommunityName(String communityName);
}
