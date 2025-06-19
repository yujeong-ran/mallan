package com.mallan.yujeongran.icebreaking.admin.repository;

import com.mallan.yujeongran.icebreaking.admin.enitity.ManagementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagementInfoRepository extends JpaRepository<ManagementInfo, Long> {

    Optional<ManagementInfo> findTopByOrderByUpdatedAtDesc();

}
