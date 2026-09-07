package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词短语对象 fun_phrase
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunPhrase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;

    /** 短语ID */
    private String phraseId;

    /** 短语文本 */
    @Excel(name = "短语文本")
    private String phraseText;

    /** 短语定义 */
    @Excel(name = "短语定义")
    private String phraseDefinition;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setPhraseId(String phraseId) 
    {
        this.phraseId = phraseId;
    }

    public String getPhraseId() 
    {
        return phraseId;
    }
    public void setPhraseText(String phraseText) 
    {
        this.phraseText = phraseText;
    }

    public String getPhraseText() 
    {
        return phraseText;
    }
    public void setPhraseDefinition(String phraseDefinition) 
    {
        this.phraseDefinition = phraseDefinition;
    }

    public String getPhraseDefinition() 
    {
        return phraseDefinition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wordId", getWordId())
            .append("phraseId", getPhraseId())
            .append("phraseText", getPhraseText())
            .append("phraseDefinition", getPhraseDefinition())
            .toString();
    }
}
