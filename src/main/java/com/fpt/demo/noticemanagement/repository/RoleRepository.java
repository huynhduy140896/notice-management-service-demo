package com.fpt.demo.noticemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fpt.demo.noticemanagement.entity.Role;

@EnableJpaRepositories
public interface RoleRepository extends GenericRepository<Role, Long> {

	Optional<Role> findByName(String roleName);
}
