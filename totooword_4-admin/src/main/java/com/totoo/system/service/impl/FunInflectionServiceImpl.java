package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunInflectionMapper;
import com.totoo.system.domain.FunInflection;
import com.totoo.system.service.IFunInflectionService;

/**
 * 单词时态变形Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunInflectionServiceImpl implements IFunInflectionService 
{
    @Autowired
    private FunInflectionMapper funInflectionMapper;

    /**
     * 查询单词时态变形
     * 
     * @param wordId 单词时态变形主键
     * @return 单词时态变形
     */
    @Override
    public FunInflection selectFunInflectionByWordId(String wordId)
    {
        return funInflectionMapper.selectFunInflectionByWordId(wordId);
    }

    /**
     * 查询单词时态变形列表
     * 
     * @param funInflection 单词时态变形
     * @return 单词时态变形
     */
    @Override
    public List<FunInflection> selectFunInflectionList(FunInflection funInflection)
    {
        return funInflectionMapper.selectFunInflectionList(funInflection);
    }

    /**
     * 新增单词时态变形
     * 
     * @param funInflection 单词时态变形
     * @return 结果
     */
    @Override
    public int insertFunInflection(FunInflection funInflection)
    {
        return funInflectionMapper.insertFunInflection(funInflection);
    }

    /**
     * 修改单词时态变形
     * 
     * @param funInflection 单词时态变形
     * @return 结果
     */
    @Override
    public int updateFunInflection(FunInflection funInflection)
    {
        return funInflectionMapper.updateFunInflection(funInflection);
    }

    /**
     * 批量删除单词时态变形
     * 
     * @param wordIds 需要删除的单词时态变形主键
     * @return 结果
     */
    @Override
    public int deleteFunInflectionByWordIds(String[] wordIds)
    {
        return funInflectionMapper.deleteFunInflectionByWordIds(wordIds);
    }

    /**
     * 删除单词时态变形信息
     * 
     * @param wordId 单词时态变形主键
     * @return 结果
     */
    @Override
    public int deleteFunInflectionByWordId(String wordId)
    {
        return funInflectionMapper.deleteFunInflectionByWordId(wordId);
    }
}
