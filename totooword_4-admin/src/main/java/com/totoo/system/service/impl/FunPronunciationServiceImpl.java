package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunPronunciationMapper;
import com.totoo.system.domain.FunPronunciation;
import com.totoo.system.service.IFunPronunciationService;

/**
 * 单词发音Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunPronunciationServiceImpl implements IFunPronunciationService 
{
    @Autowired
    private FunPronunciationMapper funPronunciationMapper;

    /**
     * 查询单词发音
     * 
     * @param wordId 单词发音主键
     * @return 单词发音
     */
    @Override
    public FunPronunciation selectFunPronunciationByWordId(String wordId)
    {
        return funPronunciationMapper.selectFunPronunciationByWordId(wordId);
    }

    /**
     * 查询单词发音列表
     * 
     * @param funPronunciation 单词发音
     * @return 单词发音
     */
    @Override
    public List<FunPronunciation> selectFunPronunciationList(FunPronunciation funPronunciation)
    {
        return funPronunciationMapper.selectFunPronunciationList(funPronunciation);
    }

    /**
     * 新增单词发音
     * 
     * @param funPronunciation 单词发音
     * @return 结果
     */
    @Override
    public int insertFunPronunciation(FunPronunciation funPronunciation)
    {
        return funPronunciationMapper.insertFunPronunciation(funPronunciation);
    }

    /**
     * 修改单词发音
     * 
     * @param funPronunciation 单词发音
     * @return 结果
     */
    @Override
    public int updateFunPronunciation(FunPronunciation funPronunciation)
    {
        return funPronunciationMapper.updateFunPronunciation(funPronunciation);
    }

    /**
     * 批量删除单词发音
     * 
     * @param wordIds 需要删除的单词发音主键
     * @return 结果
     */
    @Override
    public int deleteFunPronunciationByWordIds(String[] wordIds)
    {
        return funPronunciationMapper.deleteFunPronunciationByWordIds(wordIds);
    }

    /**
     * 删除单词发音信息
     * 
     * @param wordId 单词发音主键
     * @return 结果
     */
    @Override
    public int deleteFunPronunciationByWordId(String wordId)
    {
        return funPronunciationMapper.deleteFunPronunciationByWordId(wordId);
    }
}
