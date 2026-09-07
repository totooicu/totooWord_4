package com.totoo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 用户好友关系对象 fun_friendship
 *
 * @author ruoyi
 * @date 2025-01-25
 */
public class FunFriendship extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 好友关系ID */
    private String friendshipId;

    /** 用户1ID */
    @Excel(name = "用户1ID")
    private Long userId1;

    /** 用户2ID */
    @Excel(name = "用户2ID")
    private Long userId2;

    /** 用户1对用户2的备注 */
    @Excel(name = "用户1对用户2的备注")
    private String remarkUser1;

    /** 用户2对用户1的备注 */
    @Excel(name = "用户2对用户1的备注")
    private String remarkUser2;

    /** 添加时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "添加时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addTime;

    public void setFriendshipId(String friendshipId)
    {
        this.friendshipId = friendshipId;
    }

    public String getFriendshipId()
    {
        return friendshipId;
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
    public void setRemarkUser1(String remarkUser1)
    {
        this.remarkUser1 = remarkUser1;
    }

    public String getRemarkUser1()
    {
        return remarkUser1;
    }
    public void setRemarkUser2(String remarkUser2)
    {
        this.remarkUser2 = remarkUser2;
    }

    public String getRemarkUser2()
    {
        return remarkUser2;
    }
    public void setAddTime(Date addTime)
    {
        this.addTime = addTime;
    }

    public Date getAddTime()
    {
        return addTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("friendshipId", getFriendshipId())
                .append("userId1", getUserId1())
                .append("userId2", getUserId2())
                .append("remarkUser1", getRemarkUser1())
                .append("remarkUser2", getRemarkUser2())
                .append("addTime", getAddTime())
                .toString();
    }
}
