package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunWord;

/**
 * 单词基本信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface FunWordMapper 
{
    /**
     * 查询单词基本信息
     * 
     * @param wordId 单词基本信息主键
     * @return 单词基本信息
     */
    public FunWord selectFunWordByWordId(String wordId);

    /**
     * 查询单词基本信息列表
     * 
     * @param funWord 单词基本信息
     * @return 单词基本信息集合
     */
    public List<FunWord> selectFunWordList(FunWord funWord);

    /**
     * 新增单词基本信息
     * 
     * @param funWord 单词基本信息
     * @return 结果
     */
    public int insertFunWord(FunWord funWord);

    /**
     * 修改单词基本信息
     * 
     * @param funWord 单词基本信息
     * @return 结果
     */
    public int updateFunWord(FunWord funWord);

    /**
     * 删除单词基本信息
     * 
     * @param wordId 单词基本信息主键
     * @return 结果
     */
    public int deleteFunWordByWordId(String wordId);

    /**
     * 批量删除单词基本信息
     * 
     * @param wordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunWordByWordIds(String[] wordIds);
}
