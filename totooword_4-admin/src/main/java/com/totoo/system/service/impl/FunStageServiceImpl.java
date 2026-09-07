package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunStageMapper;
import com.totoo.system.domain.FunStage;
import com.totoo.system.service.IFunStageService;

/**
 * 单词阶段要求Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunStageServiceImpl implements IFunStageService 
{
    @Autowired
    private FunStageMapper funStageMapper;

    /**
     * 查询单词阶段要求
     * 
     * @param wordId 单词阶段要求主键
     * @return 单词阶段要求
     */
    @Override
    public FunStage selectFunStageByWordId(String wordId)
    {
        return funStageMapper.selectFunStageByWordId(wordId);
    }

    /**
     * 查询单词阶段要求列表
     * 
     * @param funStage 单词阶段要求
     * @return 单词阶段要求
     */
    @Override
    public List<FunStage> selectFunStageList(FunStage funStage)
    {
        return funStageMapper.selectFunStageList(funStage);
    }

    /**
     * 新增单词阶段要求
     * 
     * @param funStage 单词阶段要求
     * @return 结果
     */
    @Override
    public int insertFunStage(FunStage funStage)
    {
        return funStageMapper.insertFunStage(funStage);
    }

    /**
     * 修改单词阶段要求
     * 
     * @param funStage 单词阶段要求
     * @return 结果
     */
    @Override
    public int updateFunStage(FunStage funStage)
    {
        return funStageMapper.updateFunStage(funStage);
    }

    /**
     * 批量删除单词阶段要求
     * 
     * @param wordIds 需要删除的单词阶段要求主键
     * @return 结果
     */
    @Override
    public int deleteFunStageByWordIds(String[] wordIds)
    {
        return funStageMapper.deleteFunStageByWordIds(wordIds);
    }

    /**
     * 删除单词阶段要求信息
     * 
     * @param wordId 单词阶段要求主键
     * @return 结果
     */
    @Override
    public int deleteFunStageByWordId(String wordId)
    {
        return funStageMapper.deleteFunStageByWordId(wordId);
    }
}
