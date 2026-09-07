package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunBookWordMapper;
import com.totoo.system.domain.FunBookWord;
import com.totoo.system.service.IFunBookWordService;

/**
 * 书本单词关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunBookWordServiceImpl implements IFunBookWordService 
{
    @Autowired
    private FunBookWordMapper funBookWordMapper;

    /**
     * 查询书本单词关联
     * 
     * @param bookId 书本单词关联主键
     * @return 书本单词关联
     */
    @Override
    public FunBookWord selectFunBookWordByBookId(String bookId)
    {
        return funBookWordMapper.selectFunBookWordByBookId(bookId);
    }

    /**
     * 查询书本单词关联列表
     * 
     * @param funBookWord 书本单词关联
     * @return 书本单词关联
     */
    @Override
    public List<FunBookWord> selectFunBookWordList(FunBookWord funBookWord)
    {
        return funBookWordMapper.selectFunBookWordList(funBookWord);
    }

    /**
     * 新增书本单词关联
     * 
     * @param funBookWord 书本单词关联
     * @return 结果
     */
    @Override
    public int insertFunBookWord(FunBookWord funBookWord)
    {
        return funBookWordMapper.insertFunBookWord(funBookWord);
    }

    /**
     * 修改书本单词关联
     * 
     * @param funBookWord 书本单词关联
     * @return 结果
     */
    @Override
    public int updateFunBookWord(FunBookWord funBookWord)
    {
        return funBookWordMapper.updateFunBookWord(funBookWord);
    }

    /**
     * 批量删除书本单词关联
     * 
     * @param bookIds 需要删除的书本单词关联主键
     * @return 结果
     */
    @Override
    public int deleteFunBookWordByBookIds(String[] bookIds)
    {
        return funBookWordMapper.deleteFunBookWordByBookIds(bookIds);
    }

    /**
     * 删除书本单词关联信息
     * 
     * @param bookId 书本单词关联主键
     * @return 结果
     */
    @Override
    public int deleteFunBookWordByBookId(String bookId)
    {
        return funBookWordMapper.deleteFunBookWordByBookId(bookId);
    }


    @Override
    public List<Integer> searchBookIdsByWordIdSelf(Integer wordId, Long userId) {
        return funBookWordMapper.searchBookIdsByWordIdSelf( wordId,  userId);
    }

}
