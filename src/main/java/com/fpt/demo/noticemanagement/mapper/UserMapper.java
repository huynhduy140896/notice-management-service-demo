package com.fpt.demo.noticemanagement.mapper;

import org.mapstruct.Mapper;

import com.fpt.demo.noticemanagement.entity.User;
import com.fpt.demo.noticemanagement.model.UserDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements AbstractMapper<User, UserDto>{

}
