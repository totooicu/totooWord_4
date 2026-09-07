package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunBookWord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 书本单词关联Service接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IFunBookWordService 
{
    /**
     * 查询书本单词关联
     * 
     * @param bookId 书本单词关联主键
     * @return 书本单词关联
     */
    public FunBookWord selectFunBookWordByBookId(String bookId);

    /**
     * 查询书本单词关联列表
     * 
     * @param funBookWord 书本单词关联
     * @return 书本单词关联集合
     */
    public List<FunBookWord> selectFunBookWordList(FunBookWord funBookWord);

    /**
     * 新增书本单词关联
     * 
     * @param funBookWord 书本单词关联
     * @return 结果
     */
    public int insertFunBookWord(FunBookWord funBookWord);

    /**
     * 修改书本单词关联
     * 
     * @param funBookWord 书本单词关联
     * @return 结果
     */
    public int updateFunBookWord(FunBookWord funBookWord);

    /**
     * 批量删除书本单词关联
     * 
     * @param bookIds 需要删除的书本单词关联主键集合
     * @return 结果
     */
    public int deleteFunBookWordByBookIds(String[] bookIds);

    /**
     * 删除书本单词关联信息
     * 
     * @param bookId 书本单词关联主键
     * @return 结果
     */
    public int deleteFunBookWordByBookId(String bookId);
    List<Integer> searchBookIdsByWordIdSelf(@Param("wordId") Integer wordId, @Param("userId") Long userId);
}
