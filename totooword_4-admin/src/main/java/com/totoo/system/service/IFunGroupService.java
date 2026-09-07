package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunGroup;

/**
 * 群组Service接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface IFunGroupService 
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
     * 批量删除群组
     * 
     * @param groupIds 需要删除的群组主键集合
     * @return 结果
     */
    public int deleteFunGroupByGroupIds(Long[] groupIds);

    /**
     * 删除群组信息
     * 
     * @param groupId 群组主键
     * @return 结果
     */
    public int deleteFunGroupByGroupId(Long groupId);
    public List<FunGroup> witchGroupIn(Long userId );
    public List<FunGroup> witchGroupAwaited(Long userId );

    List<FunGroup> witchGroupRefused(Long userId);

    List<FunGroup> getHostGroupsByUserId(Long userId);

    List<FunGroup> witchGroupUserIsManager(Long userId);

    List<FunGroup> witchGroupUserCanManage(Long userId);
}
