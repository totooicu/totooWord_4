package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunPhraseMapper;
import com.totoo.system.domain.FunPhrase;
import com.totoo.system.service.IFunPhraseService;

/**
 * 单词短语Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunPhraseServiceImpl implements IFunPhraseService 
{
    @Autowired
    private FunPhraseMapper funPhraseMapper;

    /**
     * 查询单词短语
     * 
     * @param wordId 单词短语主键
     * @return 单词短语
     */
    @Override
    public FunPhrase selectFunPhraseByWordId(String wordId)
    {
        return funPhraseMapper.selectFunPhraseByWordId(wordId);
    }

    /**
     * 查询单词短语列表
     * 
     * @param funPhrase 单词短语
     * @return 单词短语
     */
    @Override
    public List<FunPhrase> selectFunPhraseList(FunPhrase funPhrase)
    {
        return funPhraseMapper.selectFunPhraseList(funPhrase);
    }

    /**
     * 新增单词短语
     * 
     * @param funPhrase 单词短语
     * @return 结果
     */
    @Override
    public int insertFunPhrase(FunPhrase funPhrase)
    {
        return funPhraseMapper.insertFunPhrase(funPhrase);
    }

    /**
     * 修改单词短语
     * 
     * @param funPhrase 单词短语
     * @return 结果
     */
    @Override
    public int updateFunPhrase(FunPhrase funPhrase)
    {
        return funPhraseMapper.updateFunPhrase(funPhrase);
    }

    /**
     * 批量删除单词短语
     * 
     * @param wordIds 需要删除的单词短语主键
     * @return 结果
     */
    @Override
    public int deleteFunPhraseByWordIds(String[] wordIds)
    {
        return funPhraseMapper.deleteFunPhraseByWordIds(wordIds);
    }

    /**
     * 删除单词短语信息
     * 
     * @param wordId 单词短语主键
     * @return 结果
     */
    @Override
    public int deleteFunPhraseByWordId(String wordId)
    {
        return funPhraseMapper.deleteFunPhraseByWordId(wordId);
    }
}
