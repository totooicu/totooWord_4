package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunUserBookMapper;
import com.totoo.system.domain.FunUserBook;
import com.totoo.system.service.IFunUserBookService;

/**
 * 用户收藏书本Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunUserBookServiceImpl implements IFunUserBookService 
{
    @Autowired
    private FunUserBookMapper funUserBookMapper;

    /**
     * 查询用户收藏书本
     * 
     * @param userId 用户收藏书本主键
     * @return 用户收藏书本
     */
    @Override
    public List<FunUserBook> selectFunUserBookListByUserId(Long userId)
    {
        return  funUserBookMapper.selectFunUserBookListByUserId(userId);
    }

    /**
     * 查询用户收藏书本列表
     * 
     * @param funUserBook 用户收藏书本
     * @return 用户收藏书本
     */
    @Override
    public List<FunUserBook> selectFunUserBookList(FunUserBook funUserBook)
    {
        return funUserBookMapper.selectFunUserBookList(funUserBook);
    }

    /**
     * 新增用户收藏书本
     * 
     * @param funUserBook 用户收藏书本
     * @return 结果
     */
    @Override
    public int insertFunUserBook(FunUserBook funUserBook)
    {
        return funUserBookMapper.insertFunUserBook(funUserBook);
    }

    /**
     * 修改用户收藏书本
     * 
     * @param funUserBook 用户收藏书本
     * @return 结果
     */
    @Override
    public int updateFunUserBook(FunUserBook funUserBook)
    {
        return funUserBookMapper.updateFunUserBook(funUserBook);
    }

    /**
     * 批量删除用户收藏书本
     * 
     * @param userIds 需要删除的用户收藏书本主键
     * @return 结果
     */
    @Override
    public int deleteFunUserBookByUserIds(Long[] userIds)
    {
        return funUserBookMapper.deleteFunUserBookByUserIds(userIds);
    }

    /**
     * 删除用户收藏书本信息
     * 
     * @param userId 用户收藏书本主键
     * @return 结果
     */
    @Override
    public int deleteFunUserBookByUserId(Long userId)
    {
        return funUserBookMapper.deleteFunUserBookByUserId(userId);
    }
    @Override
    public int deleteFunUserBookByBookIdsUserId(Long userId,Long[] bookIds){
        return funUserBookMapper.deleteFunUserBookByBookIdsUserId(userId,bookIds);
    }
}
