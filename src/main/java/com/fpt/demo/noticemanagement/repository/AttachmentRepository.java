package com.fpt.demo.noticemanagement.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fpt.demo.noticemanagement.entity.Attachment;

@EnableJpaRepositories
public interface AttachmentRepository extends GenericRepository<Attachment, Long> {

}
