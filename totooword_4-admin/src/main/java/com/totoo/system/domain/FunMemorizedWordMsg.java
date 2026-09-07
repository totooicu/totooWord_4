package com.totoo.system.domain;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

public class FunMemorizedWordMsg {
    private static final Logger LOGGER = Logger.getLogger(FunMemorizedWordMsg.class.getName());

    public FunWordMsg wordMsg;
    public FunBookWord bookWord;
    public FunMemorized memorized;
    public Double score;

    // 假设这些常量可以根据需要调整
    private static final double LAST_STUDY_TIME_WEIGHT = 1.0; // 权重
    private static final double TOTAL_STUDY_COUNT_WEIGHT = 0.5; // 对数函数的权重，以减少影响
    private static final double CONSECUTIVE_CORRECT_WEIGHT = 1.0;
    private static final double CONSECUTIVE_ERROR_WEIGHT = 2.0; // 错误次数应该更重一些
    private static final double MASTERY_LEVEL_WEIGHT = 0.5; // 掌握程度的权重

    public Double getScore() {
        if (memorized == null) {
            LOGGER.warning("Memorized object is null, cannot calculate score.");
            return 0.0;
        }

        double lastStudyTimeScore = calculateTimeScore(memorized.getLastStudyTime());
        double totalStudyCountScore = Math.log(memorized.getTotalStudyCount() + 1) * TOTAL_STUDY_COUNT_WEIGHT; // 使用对数函数减少影响
        double consecutiveCorrectScore = Double.valueOf(memorized.getConsecutiveCorrect())* CONSECUTIVE_CORRECT_WEIGHT;
        double consecutiveErrorScore = memorized.getConsecutiveError() * CONSECUTIVE_ERROR_WEIGHT;
        double masteryLevelScore = memorized.getMasteryLevel()* MASTERY_LEVEL_WEIGHT;

        score = lastStudyTimeScore + totalStudyCountScore + consecutiveCorrectScore + consecutiveErrorScore + masteryLevelScore;

        return score;
    }

    // 计算基于最后学习时间的得分，这里假设时间越近得分越高，使用当前时间与最后学习时间的差值作为依据
    private double calculateTimeScore(Date lastStudyTime) {
        long currentTimeMillis = System.currentTimeMillis();
        long lastStudyTimeMillis = lastStudyTime.getTime();
        long timeDifferenceMillis = currentTimeMillis - lastStudyTimeMillis;

        // 将时间差转换为天数，并假设一天得1分（这个可以根据需要调整）
        double daysDifference = timeDifferenceMillis / (1000.0 * 60 * 60 * 24);
        double score = 1.0 / (1.0 + daysDifference); // 使用一个简单的衰减函数

        // 限制得分在0到LAST_STUDY_TIME_WEIGHT之间
        return Math.max(0, Math.min(LAST_STUDY_TIME_WEIGHT, score));
    }

    @Override
    public String toString() {
        return "FunMemorizedWordMsg{" +
                "wordMsg=" + wordMsg +
                ", bookWord=" + bookWord +
                ", memorized=" + memorized +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunMemorizedWordMsg that = (FunMemorizedWordMsg) o;
        return Objects.equals(wordMsg, that.wordMsg) && Objects.equals(bookWord, that.bookWord) && Objects.equals(memorized, that.memorized);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordMsg, bookWord, memorized);
    }

    public FunWordMsg getWordMsg() {
        return wordMsg;
    }

    public void setWordMsg(FunWordMsg wordMsg) {
        this.wordMsg = wordMsg;
    }

    public FunBookWord getBookWord() {
        return bookWord;
    }

    public void setBookWord(FunBookWord bookWord) {
        this.bookWord = bookWord;
    }

    public FunMemorized getMemorized() {
        return memorized;
    }

    public void setMemorized(FunMemorized memorized) {
        this.memorized = memorized;
    }
}
