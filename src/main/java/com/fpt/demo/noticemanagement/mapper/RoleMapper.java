package com.fpt.demo.noticemanagement.mapper;

import org.mapstruct.Mapper;

import com.fpt.demo.noticemanagement.entity.Role;
import com.fpt.demo.noticemanagement.model.RoleDto;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements AbstractMapper<Role, RoleDto>{

}
