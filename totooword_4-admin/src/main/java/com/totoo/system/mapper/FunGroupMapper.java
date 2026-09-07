package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunGroup;

/**
 * 群组Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface FunGroupMapper 
{
    /**
     * 查询群组
     * 
     * @param groupId 群组主键
     * @return 群组
     */
    public FunGroup selectFunGroupByGroupId(Long groupId);

    /**
     * 查询群组列表
     * 
     * @param funGroup 群组
     * @return 群组集合
     */
    public List<FunGroup> selectFunGroupList(FunGroup funGroup);

    /**
     * 新增群组
     * 
     * @param funGroup 群组
     * @return 结果
     */
    public int insertFunGroup(FunGroup funGroup);

    /**
     * 修改群组
     * 
     * @param funGroup 群组
     * @return 结果
     */
    public int updateFunGroup(FunGroup funGroup);

    /**
     * 删除群组
     * 
     * @param groupId 群组主键
     * @return 结果
     */
    public int deleteFunGroupByGroupId(Long groupId);

    /**
     * 批量删除群组
     * 
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunGroupByGroupIds(Long[] groupIds);
}
