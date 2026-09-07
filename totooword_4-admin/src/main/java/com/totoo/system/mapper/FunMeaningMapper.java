package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunMeaning;

/**
 * 单词含义Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface FunMeaningMapper 
{
    /**
     * 查询单词含义
     * 
     * @param wordId 单词含义主键
     * @return 单词含义
     */
    public FunMeaning selectFunMeaningByWordId(String wordId);

    /**
     * 查询单词含义列表
     * 
     * @param funMeaning 单词含义
     * @return 单词含义集合
     */
    public List<FunMeaning> selectFunMeaningList(FunMeaning funMeaning);

    /**
     * 新增单词含义
     * 
     * @param funMeaning 单词含义
     * @return 结果
     */
    public int insertFunMeaning(FunMeaning funMeaning);

    /**
     * 修改单词含义
     * 
     * @param funMeaning 单词含义
     * @return 结果
     */
    public int updateFunMeaning(FunMeaning funMeaning);

    /**
     * 删除单词含义
     * 
     * @param wordId 单词含义主键
     * @return 结果
     */
    public int deleteFunMeaningByWordId(String wordId);

    /**
     * 批量删除单词含义
     * 
     * @param wordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunMeaningByWordIds(String[] wordIds);
}
