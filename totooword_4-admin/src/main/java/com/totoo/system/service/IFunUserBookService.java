package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunUserBook;

/**
 * 用户收藏书本Service接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IFunUserBookService 
{
    /**
     * 查询用户收藏书本
     * 
     * @param userId 用户收藏书本主键
     * @return 用户收藏书本
     */
    public List<FunUserBook> selectFunUserBookListByUserId(Long userId);

    /**
     * 查询用户收藏书本列表
     * 
     * @param funUserBook 用户收藏书本
     * @return 用户收藏书本集合
     */
    public List<FunUserBook> selectFunUserBookList(FunUserBook funUserBook);

    /**
     * 新增用户收藏书本
     * 
     * @param funUserBook 用户收藏书本
     * @return 结果
     */
    public int insertFunUserBook(FunUserBook funUserBook);

    /**
     * 修改用户收藏书本
     * 
     * @param funUserBook 用户收藏书本
     * @return 结果
     */
    public int updateFunUserBook(FunUserBook funUserBook);

    /**
     * 批量删除用户收藏书本
     * 
     * @param userIds 需要删除的用户收藏书本主键集合
     * @return 结果
     */
    public int deleteFunUserBookByUserIds(Long[] userIds);

    /**
     * 删除用户收藏书本信息
     * 
     * @param userId 用户收藏书本主键
     * @return 结果
     */
    public int deleteFunUserBookByUserId(Long userId);

    int deleteFunUserBookByBookIdsUserId(Long userId,Long[] bookIds);
}
