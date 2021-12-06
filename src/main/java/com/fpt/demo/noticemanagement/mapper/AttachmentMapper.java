package com.fpt.demo.noticemanagement.mapper;

import org.mapstruct.Mapper;

import com.fpt.demo.noticemanagement.entity.Attachment;
import com.fpt.demo.noticemanagement.model.AttachmentDto;

@Mapper(componentModel = "spring")
public abstract class AttachmentMapper implements AbstractMapper<Attachment, AttachmentDto> {

}
