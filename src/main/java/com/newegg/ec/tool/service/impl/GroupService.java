package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.GroupDao;
import com.newegg.ec.tool.entity.Group;
import com.newegg.ec.tool.service.IGroupService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */

@Service
public class GroupService implements IGroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private AppService appService;

    @Override
    public List<Group> getGroupList() {
        try {
            return groupDao.selectGroupList();
        } catch (Exception e) {
            logger.error("get group list error.", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Group getGroupById(String groupId) {
        if (StringUtils.isBlank(groupId)) {
            return null;
        }
        try {
            return groupDao.selectGroupById(groupId);
        } catch (Exception e) {
            logger.error("get group error, id=" + groupId, e);
        }
        return null;
    }

    @Override
    public boolean saveGroup(Group group) {
        if (group == null || StringUtils.isBlank(group.getGroupName())) {
            return false;
        }
        String groupId = group.getGroupId();
        try {
            Group existGroup = groupDao.selectGroupByName(groupId, group.getGroupName());
            if (existGroup != null) {
                return false;
            }

            group.setUpdateTime(CommonUtils.getCurrentTimestamp());
            if (StringUtils.isBlank(groupId)) {
                group.setGroupId(CommonUtils.getUUID());
                return groupDao.addGroup(group) > 0;
            }
            return groupDao.updateGroup(group) > 0;
        } catch (Exception e) {
            logger.error("save group error, " + group, e);
            return false;
        }
    }

    @Override
    public boolean deleteGroupById(String groupId) {
        if (StringUtils.isBlank(groupId)) {
            return false;
        }
        try {
            appService.deleteServiceModelByGroupId(groupId);
            groupDao.deleteGroupById(groupId);
            return true;
        } catch (Exception e) {
            logger.error("delete group error, groupId=" + groupId, e);
            return false;
        }
    }
}
