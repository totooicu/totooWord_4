package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunBookMapper;
import com.totoo.system.domain.FunBook;
import com.totoo.system.service.IFunBookService;

/**
 * 书本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunBookServiceImpl implements IFunBookService 
{
    @Autowired
    private FunBookMapper funBookMapper;

    /**
     * 查询书本信息
     * 
     * @param bookId 书本信息主键
     * @return 书本信息
     */
    @Override
    public FunBook selectFunBookByBookId(String bookId)
    {
        return funBookMapper.selectFunBookByBookId(bookId);
    }

    /**
     * 查询书本信息列表
     * 
     * @param funBook 书本信息
     * @return 书本信息
     */
    @Override
    public List<FunBook> selectFunBookList(FunBook funBook)
    {
        return funBookMapper.selectFunBookList(funBook);
    }

    /**
     * 新增书本信息
     * 
     * @param funBook 书本信息
     * @return 结果
     */
    @Override
    public int insertFunBook(FunBook funBook)
    {
        return funBookMapper.insertFunBook(funBook);
    }

    /**
     * 修改书本信息
     * 
     * @param funBook 书本信息
     * @return 结果
     */
    @Override
    public int updateFunBook(FunBook funBook)
    {
        return funBookMapper.updateFunBook(funBook);
    }

    /**
     * 批量删除书本信息
     * 
     * @param bookIds 需要删除的书本信息主键
     * @return 结果
     */
    @Override
    public int deleteFunBookByBookIds(String[] bookIds)
    {
        return funBookMapper.deleteFunBookByBookIds(bookIds);
    }

    /**
     * 删除书本信息信息
     * 
     * @param bookId 书本信息主键
     * @return 结果
     */
    @Override
    public int deleteFunBookByBookId(String bookId)
    {
        return funBookMapper.deleteFunBookByBookId(bookId);
    }
}
