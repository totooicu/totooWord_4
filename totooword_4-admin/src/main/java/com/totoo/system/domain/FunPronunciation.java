package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词发音对象 fun_pronunciation
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunPronunciation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;

    /** 发音类型 */
    private String pronunciationType;

    /** 音标转录 */
    @Excel(name = "音标转录")
    private String transcription;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setPronunciationType(String pronunciationType) 
    {
        this.pronunciationType = pronunciationType;
    }

    public String getPronunciationType() 
    {
        return pronunciationType;
    }
    public void setTranscription(String transcription) 
    {
        this.transcription = transcription;
    }

    public String getTranscription() 
    {
        return transcription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wordId", getWordId())
            .append("pronunciationType", getPronunciationType())
            .append("transcription", getTranscription())
            .toString();
    }
}
