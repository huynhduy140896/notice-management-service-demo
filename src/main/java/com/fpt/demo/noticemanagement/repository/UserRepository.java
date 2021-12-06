package com.fpt.demo.noticemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fpt.demo.noticemanagement.entity.User;

@EnableJpaRepositories
public interface UserRepository extends GenericRepository<User, Long>{

	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
