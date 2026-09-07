package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunMemorizedUserConfigMapper;
import com.totoo.system.domain.FunMemorizedUserConfig;
import com.totoo.system.service.IFunMemorizedUserConfigService;

/**
 * 用户学习配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
@Service
public class FunMemorizedUserConfigServiceImpl implements IFunMemorizedUserConfigService 
{
    @Autowired
    private FunMemorizedUserConfigMapper funMemorizedUserConfigMapper;

    /**
     * 查询用户学习配置
     * 
     * @param userId 用户学习配置主键
     * @return 用户学习配置
     */
    @Override
    public FunMemorizedUserConfig selectFunMemorizedUserConfigByUserId(Long userId)
    {
        return funMemorizedUserConfigMapper.selectFunMemorizedUserConfigByUserId(userId);
    }

    /**
     * 查询用户学习配置列表
     * 
     * @param funMemorizedUserConfig 用户学习配置
     * @return 用户学习配置
     */
    @Override
    public List<FunMemorizedUserConfig> selectFunMemorizedUserConfigList(FunMemorizedUserConfig funMemorizedUserConfig)
    {
        return funMemorizedUserConfigMapper.selectFunMemorizedUserConfigList(funMemorizedUserConfig);
    }

    /**
     * 新增用户学习配置
     * 
     * @param funMemorizedUserConfig 用户学习配置
     * @return 结果
     */
    @Override
    public int insertFunMemorizedUserConfig(FunMemorizedUserConfig funMemorizedUserConfig)
    {
        return funMemorizedUserConfigMapper.insertFunMemorizedUserConfig(funMemorizedUserConfig);
    }

    /**
     * 修改用户学习配置
     * 
     * @param funMemorizedUserConfig 用户学习配置
     * @return 结果
     */
    @Override
    public int updateFunMemorizedUserConfig(FunMemorizedUserConfig funMemorizedUserConfig)
    {
        return funMemorizedUserConfigMapper.updateFunMemorizedUserConfig(funMemorizedUserConfig);
    }

    /**
     * 批量删除用户学习配置
     * 
     * @param userIds 需要删除的用户学习配置主键
     * @return 结果
     */
    @Override
    public int deleteFunMemorizedUserConfigByUserIds(Long[] userIds)
    {
        return funMemorizedUserConfigMapper.deleteFunMemorizedUserConfigByUserIds(userIds);
    }

    /**
     * 删除用户学习配置信息
     * 
     * @param userId 用户学习配置主键
     * @return 结果
     */
    @Override
    public int deleteFunMemorizedUserConfigByUserId(Long userId)
    {
        return funMemorizedUserConfigMapper.deleteFunMemorizedUserConfigByUserId(userId);
    }
}
