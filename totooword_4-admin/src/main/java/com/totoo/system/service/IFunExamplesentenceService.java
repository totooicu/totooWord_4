package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunExamplesentence;

/**
 * 单词例句Service接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IFunExamplesentenceService 
{
    /**
     * 查询单词例句
     * 
     * @param wordId 单词例句主键
     * @return 单词例句
     */
    public FunExamplesentence selectFunExamplesentenceByWordId(String wordId);

    /**
     * 查询单词例句列表
     * 
     * @param funExamplesentence 单词例句
     * @return 单词例句集合
     */
    public List<FunExamplesentence> selectFunExamplesentenceList(FunExamplesentence funExamplesentence);

    /**
     * 新增单词例句
     * 
     * @param funExamplesentence 单词例句
     * @return 结果
     */
    public int insertFunExamplesentence(FunExamplesentence funExamplesentence);

    /**
     * 修改单词例句
     * 
     * @param funExamplesentence 单词例句
     * @return 结果
     */
    public int updateFunExamplesentence(FunExamplesentence funExamplesentence);

    /**
     * 批量删除单词例句
     * 
     * @param wordIds 需要删除的单词例句主键集合
     * @return 结果
     */
    public int deleteFunExamplesentenceByWordIds(String[] wordIds);

    /**
     * 删除单词例句信息
     * 
     * @param wordId 单词例句主键
     * @return 结果
     */
    public int deleteFunExamplesentenceByWordId(String wordId);
}
