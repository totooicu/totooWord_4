package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunPronunciation;

/**
 * 单词发音Service接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IFunPronunciationService 
{
    /**
     * 查询单词发音
     * 
     * @param wordId 单词发音主键
     * @return 单词发音
     */
    public FunPronunciation selectFunPronunciationByWordId(String wordId);

    /**
     * 查询单词发音列表
     * 
     * @param funPronunciation 单词发音
     * @return 单词发音集合
     */
    public List<FunPronunciation> selectFunPronunciationList(FunPronunciation funPronunciation);

    /**
     * 新增单词发音
     * 
     * @param funPronunciation 单词发音
     * @return 结果
     */
    public int insertFunPronunciation(FunPronunciation funPronunciation);

    /**
     * 修改单词发音
     * 
     * @param funPronunciation 单词发音
     * @return 结果
     */
    public int updateFunPronunciation(FunPronunciation funPronunciation);

    /**
     * 批量删除单词发音
     * 
     * @param wordIds 需要删除的单词发音主键集合
     * @return 结果
     */
    public int deleteFunPronunciationByWordIds(String[] wordIds);

    /**
     * 删除单词发音信息
     * 
     * @param wordId 单词发音主键
     * @return 结果
     */
    public int deleteFunPronunciationByWordId(String wordId);
}
