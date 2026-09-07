package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunBook;

/**
 * 书本信息Service接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IFunBookService 
{
    /**
     * 查询书本信息
     * 
     * @param bookId 书本信息主键
     * @return 书本信息
     */
    public FunBook selectFunBookByBookId(String bookId);

    /**
     * 查询书本信息列表
     * 
     * @param funBook 书本信息
     * @return 书本信息集合
     */
    public List<FunBook> selectFunBookList(FunBook funBook);

    /**
     * 新增书本信息
     * 
     * @param funBook 书本信息
     * @return 结果
     */
    public int insertFunBook(FunBook funBook);

    /**
     * 修改书本信息
     * 
     * @param funBook 书本信息
     * @return 结果
     */
    public int updateFunBook(FunBook funBook);

    /**
     * 批量删除书本信息
     * 
     * @param bookIds 需要删除的书本信息主键集合
     * @return 结果
     */
    public int deleteFunBookByBookIds(String[] bookIds);

    /**
     * 删除书本信息信息
     * 
     * @param bookId 书本信息主键
     * @return 结果
     */
    public int deleteFunBookByBookId(String bookId);
}
