package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunPhrase;

/**
 * 单词短语Service接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IFunPhraseService 
{
    /**
     * 查询单词短语
     * 
     * @param wordId 单词短语主键
     * @return 单词短语
     */
    public FunPhrase selectFunPhraseByWordId(String wordId);

    /**
     * 查询单词短语列表
     * 
     * @param funPhrase 单词短语
     * @return 单词短语集合
     */
    public List<FunPhrase> selectFunPhraseList(FunPhrase funPhrase);

    /**
     * 新增单词短语
     * 
     * @param funPhrase 单词短语
     * @return 结果
     */
    public int insertFunPhrase(FunPhrase funPhrase);

    /**
     * 修改单词短语
     * 
     * @param funPhrase 单词短语
     * @return 结果
     */
    public int updateFunPhrase(FunPhrase funPhrase);

    /**
     * 批量删除单词短语
     * 
     * @param wordIds 需要删除的单词短语主键集合
     * @return 结果
     */
    public int deleteFunPhraseByWordIds(String[] wordIds);

    /**
     * 删除单词短语信息
     * 
     * @param wordId 单词短语主键
     * @return 结果
     */
    public int deleteFunPhraseByWordId(String wordId);
}
