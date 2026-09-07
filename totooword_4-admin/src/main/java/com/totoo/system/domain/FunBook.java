package com.totoo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 书本信息对象 fun_book
 *
 * @author ruoyi
 * @date 2025-01-25
 */
public class FunBook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书本ID */
    private String bookId;

    /** 书本拥有者ID */
    @Excel(name = "书本拥有者ID")
    private Long ownerId;

    /** 书本标题 */
    @Excel(name = "书本标题")
    private String title;

    /** 书本创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "书本创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addTime;

    /** 书本是否已上架 */
    @Excel(name = "书本是否已上架")
    private Integer isPublished;

    /** 书本详细描述 */
    @Excel(name = "书本详细描述")
    private String description;

    /** 书本简介 */
    @Excel(name = "书本简介")
    private String intro;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String language;

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookId()
    {
        return bookId;
    }
    public void setOwnerId(Long ownerId)
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId()
    {
        return ownerId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setAddTime(Date addTime)
    {
        this.addTime = addTime;
    }

    public Date getAddTime()
    {
        return addTime;
    }
    public void setIsPublished(Integer isPublished)
    {
        this.isPublished = isPublished;
    }

    public Integer getIsPublished()
    {
        return isPublished;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getIntro()
    {
        return intro;
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getLanguage()
    {
        return language;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("bookId", getBookId())
                .append("ownerId", getOwnerId())
                .append("title", getTitle())
                .append("addTime", getAddTime())
                .append("isPublished", getIsPublished())
                .append("description", getDescription())
                .append("intro", getIntro())
                .append("language", getLanguage())
                .toString();
    }
}
