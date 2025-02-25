package com.wable.www.WableServer.api.viewit.repository;

import com.wable.www.WableServer.api.viewit.domain.Viewit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewitRepository extends JpaRepository<Viewit, Long> {
}
