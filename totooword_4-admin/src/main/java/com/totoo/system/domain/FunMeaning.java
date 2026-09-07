package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词含义对象 fun_meaning
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunMeaning extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;



    /** 词性 */
    @Excel(name = "词性")
    private String meaningType;

    /** 含义定义 */
    @Excel(name = "含义定义")
    private String meaningDefinition;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }

    public void setMeaningType(String meaningType) 
    {
        this.meaningType = meaningType;
    }

    public String getMeaningType() 
    {
        return meaningType;
    }
    public void setMeaningDefinition(String meaningDefinition) 
    {
        this.meaningDefinition = meaningDefinition;
    }

    public String getMeaningDefinition() 
    {
        return meaningDefinition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wordId", getWordId())
            .append("meaningType", getMeaningType())
            .append("meaningDefinition", getMeaningDefinition())
            .toString();
    }
}
