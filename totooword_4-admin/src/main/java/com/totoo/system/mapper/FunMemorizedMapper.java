package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunMemorized;

/**
 * 单词记忆跟踪Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
public interface FunMemorizedMapper 
{
    /**
     * 查询单词记忆跟踪
     * 
     * @param userId 单词记忆跟踪主键
     * @return 单词记忆跟踪
     */
    public FunMemorized selectFunMemorizedByUserId(Long userId);

    /**
     * 查询单词记忆跟踪列表
     * 
     * @param funMemorized 单词记忆跟踪
     * @return 单词记忆跟踪集合
     */
    public List<FunMemorized> selectFunMemorizedList(FunMemorized funMemorized);

    /**
     * 新增单词记忆跟踪
     * 
     * @param funMemorized 单词记忆跟踪
     * @return 结果
     */
    public int insertFunMemorized(FunMemorized funMemorized);

    /**
     * 修改单词记忆跟踪
     * 
     * @param funMemorized 单词记忆跟踪
     * @return 结果
     */
    public int updateFunMemorized(FunMemorized funMemorized);

    /**
     * 删除单词记忆跟踪
     * 
     * @param userId 单词记忆跟踪主键
     * @return 结果
     */
    public int deleteFunMemorizedByUserId(Long userId);

    /**
     * 批量删除单词记忆跟踪
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunMemorizedByUserIds(Long[] userIds);
}
