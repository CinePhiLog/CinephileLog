package com.CinephileLog.repository;

import com.CinephileLog.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByOrderByRoleIdAsc();
}
