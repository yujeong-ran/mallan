package com.mallan.yujeongran.icebreaking.admin.repository;

import com.mallan.yujeongran.icebreaking.admin.enitity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoginId(String loginId);

}
