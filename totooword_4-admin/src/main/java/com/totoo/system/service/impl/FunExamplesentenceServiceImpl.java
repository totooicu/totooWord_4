package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunExamplesentenceMapper;
import com.totoo.system.domain.FunExamplesentence;
import com.totoo.system.service.IFunExamplesentenceService;

/**
 * 单词例句Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunExamplesentenceServiceImpl implements IFunExamplesentenceService 
{
    @Autowired
    private FunExamplesentenceMapper funExamplesentenceMapper;

    /**
     * 查询单词例句
     * 
     * @param wordId 单词例句主键
     * @return 单词例句
     */
    @Override
    public FunExamplesentence selectFunExamplesentenceByWordId(String wordId)
    {
        return funExamplesentenceMapper.selectFunExamplesentenceByWordId(wordId);
    }

    /**
     * 查询单词例句列表
     * 
     * @param funExamplesentence 单词例句
     * @return 单词例句
     */
    @Override
    public List<FunExamplesentence> selectFunExamplesentenceList(FunExamplesentence funExamplesentence)
    {
        return funExamplesentenceMapper.selectFunExamplesentenceList(funExamplesentence);
    }

    /**
     * 新增单词例句
     * 
     * @param funExamplesentence 单词例句
     * @return 结果
     */
    @Override
    public int insertFunExamplesentence(FunExamplesentence funExamplesentence)
    {
        return funExamplesentenceMapper.insertFunExamplesentence(funExamplesentence);
    }

    /**
     * 修改单词例句
     * 
     * @param funExamplesentence 单词例句
     * @return 结果
     */
    @Override
    public int updateFunExamplesentence(FunExamplesentence funExamplesentence)
    {
        return funExamplesentenceMapper.updateFunExamplesentence(funExamplesentence);
    }

    /**
     * 批量删除单词例句
     * 
     * @param wordIds 需要删除的单词例句主键
     * @return 结果
     */
    @Override
    public int deleteFunExamplesentenceByWordIds(String[] wordIds)
    {
        return funExamplesentenceMapper.deleteFunExamplesentenceByWordIds(wordIds);
    }

    /**
     * 删除单词例句信息
     * 
     * @param wordId 单词例句主键
     * @return 结果
     */
    @Override
    public int deleteFunExamplesentenceByWordId(String wordId)
    {
        return funExamplesentenceMapper.deleteFunExamplesentenceByWordId(wordId);
    }
}
