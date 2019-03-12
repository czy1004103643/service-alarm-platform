package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.Group;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */
public interface IGroupService {

    List<Group> getGroupList();

    Group getGroupById(String groupId);

    boolean saveGroup(Group group);

    boolean deleteGroupById(String groupId);

}
