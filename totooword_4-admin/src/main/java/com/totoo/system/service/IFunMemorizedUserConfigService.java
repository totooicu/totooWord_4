package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunMemorizedUserConfig;

/**
 * 用户学习配置Service接口
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
public interface IFunMemorizedUserConfigService 
{
    /**
     * 查询用户学习配置
     * 
     * @param userId 用户学习配置主键
     * @return 用户学习配置
     */
    public FunMemorizedUserConfig selectFunMemorizedUserConfigByUserId(Long userId);

    /**
     * 查询用户学习配置列表
     * 
     * @param funMemorizedUserConfig 用户学习配置
     * @return 用户学习配置集合
     */
    public List<FunMemorizedUserConfig> selectFunMemorizedUserConfigList(FunMemorizedUserConfig funMemorizedUserConfig);

    /**
     * 新增用户学习配置
     * 
     * @param funMemorizedUserConfig 用户学习配置
     * @return 结果
     */
    public int insertFunMemorizedUserConfig(FunMemorizedUserConfig funMemorizedUserConfig);

    /**
     * 修改用户学习配置
     * 
     * @param funMemorizedUserConfig 用户学习配置
     * @return 结果
     */
    public int updateFunMemorizedUserConfig(FunMemorizedUserConfig funMemorizedUserConfig);

    /**
     * 批量删除用户学习配置
     * 
     * @param userIds 需要删除的用户学习配置主键集合
     * @return 结果
     */
    public int deleteFunMemorizedUserConfigByUserIds(Long[] userIds);

    /**
     * 删除用户学习配置信息
     * 
     * @param userId 用户学习配置主键
     * @return 结果
     */
    public int deleteFunMemorizedUserConfigByUserId(Long userId);
}
