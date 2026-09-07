package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunMeaningMapper;
import com.totoo.system.domain.FunMeaning;
import com.totoo.system.service.IFunMeaningService;

/**
 * 单词含义Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunMeaningServiceImpl implements IFunMeaningService 
{
    @Autowired
    private FunMeaningMapper funMeaningMapper;

    /**
     * 查询单词含义
     * 
     * @param wordId 单词含义主键
     * @return 单词含义
     */
    @Override
    public FunMeaning selectFunMeaningByWordId(String wordId)
    {
        return funMeaningMapper.selectFunMeaningByWordId(wordId);
    }

    /**
     * 查询单词含义列表
     * 
     * @param funMeaning 单词含义
     * @return 单词含义
     */
    @Override
    public List<FunMeaning> selectFunMeaningList(FunMeaning funMeaning)
    {
        return funMeaningMapper.selectFunMeaningList(funMeaning);
    }

    /**
     * 新增单词含义
     * 
     * @param funMeaning 单词含义
     * @return 结果
     */
    @Override
    public int insertFunMeaning(FunMeaning funMeaning)
    {
        return funMeaningMapper.insertFunMeaning(funMeaning);
    }

    /**
     * 修改单词含义
     * 
     * @param funMeaning 单词含义
     * @return 结果
     */
    @Override
    public int updateFunMeaning(FunMeaning funMeaning)
    {
        return funMeaningMapper.updateFunMeaning(funMeaning);
    }

    /**
     * 批量删除单词含义
     * 
     * @param wordIds 需要删除的单词含义主键
     * @return 结果
     */
    @Override
    public int deleteFunMeaningByWordIds(String[] wordIds)
    {
        return funMeaningMapper.deleteFunMeaningByWordIds(wordIds);
    }

    /**
     * 删除单词含义信息
     * 
     * @param wordId 单词含义主键
     * @return 结果
     */
    @Override
    public int deleteFunMeaningByWordId(String wordId)
    {
        return funMeaningMapper.deleteFunMeaningByWordId(wordId);
    }
}
