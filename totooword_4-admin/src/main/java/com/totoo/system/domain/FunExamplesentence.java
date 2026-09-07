package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词例句对象 fun_examplesentence
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunExamplesentence extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;

    /** 例句ID */
    private String sentenceId;

    /** 例句文本 */
    @Excel(name = "例句文本")
    private String sentenceText;

    /** 例句定义 */
    @Excel(name = "例句定义")
    private String sentenceDefinition;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setSentenceId(String sentenceId) 
    {
        this.sentenceId = sentenceId;
    }

    public String getSentenceId() 
    {
        return sentenceId;
    }
    public void setSentenceText(String sentenceText) 
    {
        this.sentenceText = sentenceText;
    }

    public String getSentenceText() 
    {
        return sentenceText;
    }
    public void setSentenceDefinition(String sentenceDefinition) 
    {
        this.sentenceDefinition = sentenceDefinition;
    }

    public String getSentenceDefinition() 
    {
        return sentenceDefinition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wordId", getWordId())
            .append("sentenceId", getSentenceId())
            .append("sentenceText", getSentenceText())
            .append("sentenceDefinition", getSentenceDefinition())
            .toString();
    }
}
