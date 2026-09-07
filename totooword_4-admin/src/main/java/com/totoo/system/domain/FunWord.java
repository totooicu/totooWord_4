package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词基本信息对象 fun_word
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunWord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;

    /** 单词拼写 */
    @Excel(name = "单词拼写")
    private String spell;

    /** 单词所属语言 */
    @Excel(name = "单词所属语言")
    private String language;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setSpell(String spell) 
    {
        this.spell = spell;
    }

    public String getSpell() 
    {
        return spell;
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
            .append("wordId", getWordId())
            .append("spell", getSpell())
            .append("language", getLanguage())
            .toString();
    }
}
