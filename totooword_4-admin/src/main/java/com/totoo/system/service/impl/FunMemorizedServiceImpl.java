package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunMemorizedMapper;
import com.totoo.system.domain.FunMemorized;
import com.totoo.system.service.IFunMemorizedService;

/**
 * 单词记忆跟踪Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
@Service
public class FunMemorizedServiceImpl implements IFunMemorizedService 
{
    @Autowired
    private FunMemorizedMapper funMemorizedMapper;

    /**
     * 查询单词记忆跟踪
     * 
     * @param userId 单词记忆跟踪主键
     * @return 单词记忆跟踪
     */
    @Override
    public FunMemorized selectFunMemorizedByUserId(Long userId)
    {
        return funMemorizedMapper.selectFunMemorizedByUserId(userId);
    }

    /**
     * 查询单词记忆跟踪列表
     * 
     * @param funMemorized 单词记忆跟踪
     * @return 单词记忆跟踪
     */
    @Override
    public List<FunMemorized> selectFunMemorizedList(FunMemorized funMemorized)
    {
        return funMemorizedMapper.selectFunMemorizedList(funMemorized);
    }

    /**
     * 新增单词记忆跟踪
     * 
     * @param funMemorized 单词记忆跟踪
     * @return 结果
     */
    @Override
    public int insertFunMemorized(FunMemorized funMemorized)
    {
        return funMemorizedMapper.insertFunMemorized(funMemorized);
    }

    /**
     * 修改单词记忆跟踪
     * 
     * @param funMemorized 单词记忆跟踪
     * @return 结果
     */
    @Override
    public int updateFunMemorized(FunMemorized funMemorized)
    {
        return funMemorizedMapper.updateFunMemorized(funMemorized);
    }

    /**
     * 批量删除单词记忆跟踪
     * 
     * @param userIds 需要删除的单词记忆跟踪主键
     * @return 结果
     */
    @Override
    public int deleteFunMemorizedByUserIds(Long[] userIds)
    {
        return funMemorizedMapper.deleteFunMemorizedByUserIds(userIds);
    }

    /**
     * 删除单词记忆跟踪信息
     * 
     * @param userId 单词记忆跟踪主键
     * @return 结果
     */
    @Override
    public int deleteFunMemorizedByUserId(Long userId)
    {
        return funMemorizedMapper.deleteFunMemorizedByUserId(userId);
    }
}
