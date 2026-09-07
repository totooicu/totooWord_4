package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunInflection;

/**
 * 单词时态变形Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface FunInflectionMapper 
{
    /**
     * 查询单词时态变形
     * 
     * @param wordId 单词时态变形主键
     * @return 单词时态变形
     */
    public FunInflection selectFunInflectionByWordId(String wordId);

    /**
     * 查询单词时态变形列表
     * 
     * @param funInflection 单词时态变形
     * @return 单词时态变形集合
     */
    public List<FunInflection> selectFunInflectionList(FunInflection funInflection);

    /**
     * 新增单词时态变形
     * 
     * @param funInflection 单词时态变形
     * @return 结果
     */
    public int insertFunInflection(FunInflection funInflection);

    /**
     * 修改单词时态变形
     * 
     * @param funInflection 单词时态变形
     * @return 结果
     */
    public int updateFunInflection(FunInflection funInflection);

    /**
     * 删除单词时态变形
     * 
     * @param wordId 单词时态变形主键
     * @return 结果
     */
    public int deleteFunInflectionByWordId(String wordId);

    /**
     * 批量删除单词时态变形
     * 
     * @param wordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunInflectionByWordIds(String[] wordIds);
}
