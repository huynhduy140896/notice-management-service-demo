package com.fpt.demo.noticemanagement.mapper;

import org.mapstruct.Mapper;

import com.fpt.demo.noticemanagement.entity.Notice;
import com.fpt.demo.noticemanagement.model.NoticeResponse;

@Mapper(componentModel = "spring")
public abstract class NoticeMapper implements AbstractMapper<Notice, NoticeResponse>{

}
