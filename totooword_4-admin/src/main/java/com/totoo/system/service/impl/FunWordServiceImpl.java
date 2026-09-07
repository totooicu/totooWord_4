package com.totoo.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunWordMapper;
import com.totoo.system.domain.FunWord;
import com.totoo.system.service.IFunWordService;

/**
 * 单词基本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunWordServiceImpl implements IFunWordService 
{
    @Autowired
    private FunWordMapper funWordMapper;

    /**
     * 查询单词基本信息
     * 
     * @param wordId 单词基本信息主键
     * @return 单词基本信息
     */
    @Override
    public FunWord selectFunWordByWordId(String wordId)
    {
        return funWordMapper.selectFunWordByWordId(wordId);
    }
    @Override
    public List<FunWord> selectFunWordByWordIds(List<String> wordIds){
        List<FunWord> funWords =new ArrayList<>();
        for (String wordId : wordIds) {
            funWords.add( funWordMapper.selectFunWordByWordId(wordId));
        }
        return funWords;
    }
    /**
     * 查询单词基本信息列表
     * 
     * @param funWord 单词基本信息
     * @return 单词基本信息
     */
    @Override
    public List<FunWord> selectFunWordList(FunWord funWord)
    {
        return funWordMapper.selectFunWordList(funWord);
    }

    /**
     * 新增单词基本信息
     * 
     * @param funWord 单词基本信息
     * @return 结果
     */
    @Override
    public int insertFunWord(FunWord funWord)
    {
        return funWordMapper.insertFunWord(funWord);
    }

    /**
     * 修改单词基本信息
     * 
     * @param funWord 单词基本信息
     * @return 结果
     */
    @Override
    public int updateFunWord(FunWord funWord)
    {
        return funWordMapper.updateFunWord(funWord);
    }

    /**
     * 批量删除单词基本信息
     * 
     * @param wordIds 需要删除的单词基本信息主键
     * @return 结果
     */
    @Override
    public int deleteFunWordByWordIds(String[] wordIds)
    {
        return funWordMapper.deleteFunWordByWordIds(wordIds);
    }

    /**
     * 删除单词基本信息信息
     * 
     * @param wordId 单词基本信息主键
     * @return 结果
     */
    @Override
    public int deleteFunWordByWordId(String wordId)
    {
        return funWordMapper.deleteFunWordByWordId(wordId);
    }
}
