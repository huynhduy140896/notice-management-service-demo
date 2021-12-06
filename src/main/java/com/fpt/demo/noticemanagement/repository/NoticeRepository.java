package com.fpt.demo.noticemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fpt.demo.noticemanagement.entity.Notice;

@EnableJpaRepositories
public interface NoticeRepository extends GenericRepository<Notice, Long> {

	Page<Notice> findAllByIsAchievedFalse(Pageable pageable);
}
