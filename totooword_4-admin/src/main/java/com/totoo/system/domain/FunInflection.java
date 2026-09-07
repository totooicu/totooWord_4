package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词时态变形对象 fun_inflection
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunInflection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;

    /** 时态类型 */
    private String inflectionType;

    /** 时态变形 */
    @Excel(name = "时态变形")
    private String inflectionText;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setInflectionType(String inflectionType) 
    {
        this.inflectionType = inflectionType;
    }

    public String getInflectionType() 
    {
        return inflectionType;
    }
    public void setInflectionText(String inflectionText) 
    {
        this.inflectionText = inflectionText;
    }

    public String getInflectionText() 
    {
        return inflectionText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wordId", getWordId())
            .append("inflectionType", getInflectionType())
            .append("inflectionText", getInflectionText())
            .toString();
    }
}
