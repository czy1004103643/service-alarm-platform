package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */
@Repository
public interface GroupDao {

    List<Group> selectGroupList();

    Group selectGroupById(String groupId);

    Group selectGroupByName(@Param("groupId") String groupId, @Param("groupName") String groupName);

    int addGroup(Group group);

    int updateGroup(Group group);

    int deleteGroupById(String groupId);
}
