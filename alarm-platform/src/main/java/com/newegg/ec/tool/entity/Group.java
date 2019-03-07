package com.newegg.ec.tool.entity;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */
public class Group {

    private String groupId;

    private String groupName;

    private String description;

    private Timestamp updateTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Group{");
        sb.append("groupId='").append(groupId).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
