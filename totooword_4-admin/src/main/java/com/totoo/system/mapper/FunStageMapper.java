package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunStage;

/**
 * 单词阶段要求Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface FunStageMapper 
{
    /**
     * 查询单词阶段要求
     * 
     * @param wordId 单词阶段要求主键
     * @return 单词阶段要求
     */
    public FunStage selectFunStageByWordId(String wordId);

    /**
     * 查询单词阶段要求列表
     * 
     * @param funStage 单词阶段要求
     * @return 单词阶段要求集合
     */
    public List<FunStage> selectFunStageList(FunStage funStage);

    /**
     * 新增单词阶段要求
     * 
     * @param funStage 单词阶段要求
     * @return 结果
     */
    public int insertFunStage(FunStage funStage);

    /**
     * 修改单词阶段要求
     * 
     * @param funStage 单词阶段要求
     * @return 结果
     */
    public int updateFunStage(FunStage funStage);

    /**
     * 删除单词阶段要求
     * 
     * @param wordId 单词阶段要求主键
     * @return 结果
     */
    public int deleteFunStageByWordId(String wordId);

    /**
     * 批量删除单词阶段要求
     * 
     * @param wordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunStageByWordIds(String[] wordIds);
}
