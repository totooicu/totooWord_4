package com.totoo.system.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.totoo.common.core.domain.entity.SysUser;
import com.totoo.system.service.IFunGroupService;
import com.totoo.system.service.ISysUserService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 群组成员对象 fun_group_member
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public class FunGroupMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 成员ID */
    private Long memberId;

    /** 群组ID */
    @Excel(name = "群组ID")
    private Long groupId;
    private FunGroup group;

    @Override
    public String toString() {
        return "FunGroupMember{" +
                "memberId=" + memberId +
                ", groupId=" + groupId +
                ", group=" + group +
                ", userId=" + userId +
                ", user=" + user +
                ", memberName='" + memberName + '\'' +
                ", joinTime=" + joinTime +
                ", role='" + role + '\'' +
                ", latestMessageId=" + latestMessageId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunGroupMember that = (FunGroupMember) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(groupId, that.groupId) && Objects.equals(group, that.group) && Objects.equals(userId, that.userId) && Objects.equals(user, that.user) && Objects.equals(memberName, that.memberName) && Objects.equals(joinTime, that.joinTime) && Objects.equals(role, that.role) && Objects.equals(latestMessageId, that.latestMessageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, groupId, group, userId, user, memberName, joinTime, role, latestMessageId);
    }

    public FunGroup getGroup() {
        return group;
    }

    public void setGroup(FunGroup group) {
        this.group = group;
    }

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;
    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
    public FunGroupMember putUserAndClearSensitiveInfo(ISysUserService sysUserService){
        user= sysUserService.selectUserById(userId).clearSensitiveInfo();
        return this;
    }


    /** 群内展示名称（若为null则使用用户昵称） */
    @Excel(name = "群内展示名称", readConverterExp = "若=为null则使用用户昵称")
    private String memberName;

    /** 加入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "加入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinTime;

    /** 成员角色（0普通成员，1管理员，2群主） */
    @Excel(name = "成员角色", readConverterExp = "0=普通成员，1管理员，2群主")
    private String role;

    /** 最晚查看的message_id */
    @Excel(name = "最晚查看的message_id")
    private Long latestMessageId;

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }
    public Long getGroupId() 
    {
        return groupId;
    }
    public FunGroupMember putGroup( IFunGroupService funGroupService){
        group=funGroupService.selectFunGroupByGroupId(groupId);
        return this;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setMemberName(String memberName) 
    {
        this.memberName = memberName;
    }

    public String getMemberName() 
    {
        return memberName;
    }
    public void setJoinTime(Date joinTime) 
    {
        this.joinTime = joinTime;
    }

    public Date getJoinTime() 
    {
        return joinTime;
    }
    public void setRole(String role) 
    {
        this.role = role;
    }

    public String getRole() 
    {
        return role;
    }
    public void setLatestMessageId(Long latestMessageId) 
    {
        this.latestMessageId = latestMessageId;
    }

    public Long getLatestMessageId() 
    {
        return latestMessageId;
    }

}
