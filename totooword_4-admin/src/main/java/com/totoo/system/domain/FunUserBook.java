package com.totoo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 用户收藏书本对象 fun_user_book
 *
 * @author ruoyi
 * @date 2025-01-25
 */
public class FunUserBook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 书本ID */
    private String bookId;

    /** 收藏时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收藏时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectionTime;

    /** 收藏备注 */
    @Excel(name = "收藏备注")
    private String collectionNote;

    /** 是否拥有此书 */
    @Excel(name = "是否拥有此书")
    private Integer isOwned;

    /** 获取途径 */
    @Excel(name = "获取途径")
    private String acquisitionMethod;

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookId()
    {
        return bookId;
    }
    public void setCollectionTime(Date collectionTime)
    {
        this.collectionTime = collectionTime;
    }

    public Date getCollectionTime()
    {
        return collectionTime;
    }
    public void setCollectionNote(String collectionNote)
    {
        this.collectionNote = collectionNote;
    }

    public String getCollectionNote()
    {
        return collectionNote;
    }
    public void setIsOwned(Integer isOwned)
    {
        this.isOwned = isOwned;
    }

    public Integer getIsOwned()
    {
        return isOwned;
    }
    public void setAcquisitionMethod(String acquisitionMethod)
    {
        this.acquisitionMethod = acquisitionMethod;
    }

    public String getAcquisitionMethod()
    {
        return acquisitionMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("bookId", getBookId())
                .append("collectionTime", getCollectionTime())
                .append("collectionNote", getCollectionNote())
                .append("isOwned", getIsOwned())
                .append("acquisitionMethod", getAcquisitionMethod())
                .toString();
    }
}
