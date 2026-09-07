package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 消息列对象 fun_message_list
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public class FunMessageList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息列表ID */
    private Long listId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 群/好友ID */
    @Excel(name = "群/好友ID")
    private Long targetId;

    /** 记录类型（0好友记录，1群聊记录） */
    @Excel(name = "记录类型", readConverterExp = "0=好友记录，1群聊记录")
    private String recordType;

    /** 是否置顶（0否，1是） */
    @Excel(name = "是否置顶", readConverterExp = "0=否，1是")
    private String isTop;

    /** 最新消息ID */
    @Excel(name = "最新消息ID")
    private Long latestMessageId;

    public void setListId(Long listId) 
    {
        this.listId = listId;
    }

    public Long getListId() 
    {
        return listId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTargetId(Long targetId) 
    {
        this.targetId = targetId;
    }

    public Long getTargetId() 
    {
        return targetId;
    }
    public void setRecordType(String recordType) 
    {
        this.recordType = recordType;
    }

    public String getRecordType() 
    {
        return recordType;
    }
    public void setIsTop(String isTop) 
    {
        this.isTop = isTop;
    }

    public String getIsTop() 
    {
        return isTop;
    }
    public void setLatestMessageId(Long latestMessageId) 
    {
        this.latestMessageId = latestMessageId;
    }

    public Long getLatestMessageId() 
    {
        return latestMessageId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("listId", getListId())
            .append("userId", getUserId())
            .append("targetId", getTargetId())
            .append("recordType", getRecordType())
            .append("isTop", getIsTop())
            .append("latestMessageId", getLatestMessageId())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
