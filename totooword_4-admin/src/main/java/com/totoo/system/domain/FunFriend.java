package com.totoo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 好友关系对象 fun_friend
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public class FunFriend extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 好友关系ID */
    private Long friendId;

    /** 发送申请者用户ID */
    @Excel(name = "发送申请者用户ID")
    private Long userId1;

    /** 收到验证消息者用户ID */
    @Excel(name = "收到验证消息者用户ID")
    private Long userId2;

    /** 最晚查看的message_id */
    @Excel(name = "最晚查看的message_id")
    private Long latestMessageId;

    /** 好友状态（0等待对方同意，1正常） */
    @Excel(name = "好友状态", readConverterExp = "0=等待对方同意，1正常")
    private String status;

    /** 验证消息发送时间（status=0）/成为好友时间（status=1） */
    @Excel(name = "验证消息发送时间", readConverterExp = "s=tatus=0")
    private Date actionTime;

    public void setFriendId(Long friendId) 
    {
        this.friendId = friendId;
    }

    public Long getFriendId() 
    {
        return friendId;
    }
    public void setUserId1(Long userId1) 
    {
        this.userId1 = userId1;
    }

    public Long getUserId1() 
    {
        return userId1;
    }
    public void setUserId2(Long userId2) 
    {
        this.userId2 = userId2;
    }

    public Long getUserId2() 
    {
        return userId2;
    }
    public void setLatestMessageId(Long latestMessageId) 
    {
        this.latestMessageId = latestMessageId;
    }

    public Long getLatestMessageId() 
    {
        return latestMessageId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setActionTime(Date actionTime) 
    {
        this.actionTime = actionTime;
    }

    public Date getActionTime() 
    {
        return actionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("friendId", getFriendId())
            .append("userId1", getUserId1())
            .append("userId2", getUserId2())
            .append("latestMessageId", getLatestMessageId())
            .append("status", getStatus())
            .append("actionTime", getActionTime())
            .toString();
    }
}
