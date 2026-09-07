package com.totoo.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

import java.util.Objects;

/**
 * 用户学习配置对象 fun_memorized_user_config
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
public class FunMemorizedUserConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunMemorizedUserConfig that = (FunMemorizedUserConfig) o;
        return Objects.equals(userId, that.userId) && Objects.equals(currentBookId, that.currentBookId) && Objects.equals(totalCheckinDays, that.totalCheckinDays) && Objects.equals(dailyLearningGoal, that.dailyLearningGoal) && Objects.equals(dailyReviewGoal, that.dailyReviewGoal);
    }

    @Override
    public String toString() {
        return "FunMemorizedUserConfig{" +
                "userId=" + userId +
                ", currentBookId=" + currentBookId +
                ", totalCheckinDays=" + totalCheckinDays +
                ", dailyLearningGoal=" + dailyLearningGoal +
                ", dailyReviewGoal=" + dailyReviewGoal +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, currentBookId, totalCheckinDays, dailyLearningGoal, dailyReviewGoal);
    }

    /** 正在学习的书本ID */
    @Excel(name = "正在学习的书本ID")
    private Integer currentBookId;


    public Integer getDailyReviewGoal() {
        return dailyReviewGoal;
    }

    public void setDailyReviewGoal(Integer dailyReviewGoal) {
        this.dailyReviewGoal = dailyReviewGoal;
    }

    /** 总打卡天数 */
    @Excel(name = "总打卡天数")
    private Long totalCheckinDays;

    /** 每日学习量（单词数） */
    @Excel(name = "每日学习量", readConverterExp = "单=词数")
    private Integer dailyLearningGoal;

    private Integer dailyReviewGoal;
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCurrentBookId(Integer currentBookId) 
    {
        this.currentBookId = currentBookId;
    }

    public Integer getCurrentBookId() 
    {
        return currentBookId;
    }
    public void setTotalCheckinDays(Integer totalCheckinDays) 
    {
        if(totalCheckinDays == null)this.totalCheckinDays=null;
        else this.totalCheckinDays = Long.valueOf(totalCheckinDays);
    }

    public Long getTotalCheckinDays()
    {
        return totalCheckinDays;
    }
    public void setDailyLearningGoal(Integer dailyLearningGoal) 
    {
        this.dailyLearningGoal = dailyLearningGoal;
    }

    public Integer getDailyLearningGoal() 
    {
        return dailyLearningGoal;
    }

}
