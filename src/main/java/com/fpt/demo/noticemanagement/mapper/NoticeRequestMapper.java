package com.fpt.demo.noticemanagement.mapper;

import org.mapstruct.Mapper;

import com.fpt.demo.noticemanagement.entity.Notice;
import com.fpt.demo.noticemanagement.model.NoticeRequest;

@Mapper(componentModel = "spring")
public abstract class NoticeRequestMapper implements AbstractMapper<Notice, NoticeRequest>{

}
