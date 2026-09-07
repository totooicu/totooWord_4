package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词阶段要求对象 fun_stage
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class FunStage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单词ID */
    private String wordId;

    /** 考试等级 */
    private String stageText;

    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setStageText(String stageText) 
    {
        this.stageText = stageText;
    }

    public String getStageText() 
    {
        return stageText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wordId", getWordId())
            .append("stageText", getStageText())
            .toString();
    }
}
