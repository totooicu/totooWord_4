package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

import java.util.List;
import java.util.Objects;

/**
 * 群组对象 fun_group
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public class FunGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 群组ID */
    private Long groupId;

    /** 群组名称 */
    @Excel(name = "群组名称")
    private String groupName;

    /** 创建者用户ID */
    @Excel(name = "创建者用户ID")
    private Long createUserId;

    /** 群组简介 */
    @Excel(name = "群组简介")
    private String introduction;

    /** 群组头像链接 */
    @Excel(name = "群组头像链接")
    private String avatarUrl;

    @Override
    public String toString() {
        return "FunGroup{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", createUserId=" + createUserId +
                ", introduction='" + introduction + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", groupMembers=" + groupMembers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunGroup funGroup = (FunGroup) o;
        return Objects.equals(groupId, funGroup.groupId) && Objects.equals(groupName, funGroup.groupName) && Objects.equals(createUserId, funGroup.createUserId) && Objects.equals(introduction, funGroup.introduction) && Objects.equals(avatarUrl, funGroup.avatarUrl) && Objects.equals(groupMembers, funGroup.groupMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, createUserId, introduction, avatarUrl, groupMembers);
    }

    public List<FunGroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<FunGroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    private List<FunGroupMember> groupMembers;
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }
    public void setAvatarUrl(String avatarUrl) 
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() 
    {
        return avatarUrl;
    }

}
