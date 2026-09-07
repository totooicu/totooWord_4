package com.totoo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 书本单词关联对象 fun_book_word
 *
 * @author ruoyi
 * @date 2025-01-25
 */
public class FunBookWord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书本ID */
    private String bookId;

    /** 单词ID */
    private String wordId;

    /** 用户注释，JSON格式 */
    @Excel(name = "用户注释，JSON格式")
    private String annotation;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date addTime;

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookId()
    {
        return bookId;
    }
    public void setWordId(String wordId)
    {
        this.wordId = wordId;
    }

    public String getWordId()
    {
        return wordId;
    }
    public void setAnnotation(String annotation)
    {
        this.annotation = annotation;
    }

    public String getAnnotation()
    {
        return annotation;
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
                .append("bookId", getBookId())
                .append("wordId", getWordId())
                .append("annotation", getAnnotation())
                .append("addTime", getAddTime())
                .toString();
    }
}
