package com.totoo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 单词记忆跟踪对象 fun_memorized
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
public class FunMemorized extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 单词ID */
    private String wordId;

    /** 最后学习时间 */
    private Date lastStudyTime;

    /** 总学习次数 */
    @Excel(name = "总学习次数")
    private Long totalStudyCount;

    /** 连续正确次数 */
    @Excel(name = "连续正确次数")
    private Long consecutiveCorrect;

    /** 掌握程度 */
    @Excel(name = "掌握程度")
    private Long masteryLevel;

    /** 上次学习得分 */
    @Excel(name = "上次学习得分")
    private Long lastScore;

    /** 连续错误次数 */
    @Excel(name = "连续错误次数")
    private Long consecutiveError;

    /** 拼写数据{
    avg_time: INT,       
    error_rate: DECIMAL(4,3),
    last_attempt: TEXT   
  } */
//    @Excel(name = "拼写数据{
//    avg_time: INT,
//    error_rate: DECIMAL(4,3),
//    last_attempt: TEXT
//  }")
    private String spellData;


    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setWordId(String wordId) 
    {
        this.wordId = wordId;
    }

    public String getWordId() 
    {
        return wordId;
    }
    public void setLastStudyTime(Date lastStudyTime) 
    {
        this.lastStudyTime = lastStudyTime;
    }

    public Date getLastStudyTime() 
    {
        return lastStudyTime;
    }
    public void setTotalStudyCount(Long totalStudyCount) 
    {
        this.totalStudyCount = totalStudyCount;
    }

    public Long getTotalStudyCount() 
    {
        return totalStudyCount;
    }
    public void setConsecutiveCorrect(Long consecutiveCorrect)
    {
        this.consecutiveCorrect = consecutiveCorrect;
    }

    public Long getConsecutiveCorrect()
    {
        return consecutiveCorrect;
    }
    public void setMasteryLevel(Long masteryLevel) 
    {
        this.masteryLevel = masteryLevel;
    }

    public Long getMasteryLevel() 
    {
        return masteryLevel;
    }
    public void setLastScore(Long lastScore) 
    {
        this.lastScore = lastScore;
    }

    public Long getLastScore() 
    {
        return lastScore;
    }
    public void setConsecutiveError(Long consecutiveError) 
    {
        this.consecutiveError = consecutiveError;
    }

    public Long getConsecutiveError() 
    {
        return consecutiveError;
    }
    public void setSpellData(String spellData) 
    {
        this.spellData = spellData;
    }

    public String getSpellData() 
    {
        return spellData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("wordId", getWordId())
            .append("lastStudyTime", getLastStudyTime())
            .append("totalStudyCount", getTotalStudyCount())
            .append("consecutiveCorrect", getConsecutiveCorrect())
            .append("masteryLevel", getMasteryLevel())
            .append("lastScore", getLastScore())
            .append("consecutiveError", getConsecutiveError())
            .append("spellData", getSpellData())
            .toString();
    }
}
