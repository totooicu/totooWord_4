package com.totoo.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunFriendshipMapper;
import com.totoo.system.domain.FunFriendship;
import com.totoo.system.service.IFunFriendshipService;

/**
 * 用户好友关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class FunFriendshipServiceImpl implements IFunFriendshipService 
{
    @Autowired
    private FunFriendshipMapper funFriendshipMapper;

    /**
     * 查询用户好友关系
     * 
     * @param friendshipId 用户好友关系主键
     * @return 用户好友关系
     */
    @Override
    public FunFriendship selectFunFriendshipByFriendshipId(String friendshipId)
    {
        return funFriendshipMapper.selectFunFriendshipByFriendshipId(friendshipId);
    }

    /**
     * 查询用户好友关系列表
     * 
     * @param funFriendship 用户好友关系
     * @return 用户好友关系
     */
    @Override
    public List<FunFriendship> selectFunFriendshipList(FunFriendship funFriendship)
    {
        return funFriendshipMapper.selectFunFriendshipList(funFriendship);
    }

    /**
     * 新增用户好友关系
     * 
     * @param funFriendship 用户好友关系
     * @return 结果
     */
    @Override
    public int insertFunFriendship(FunFriendship funFriendship)
    {
        return funFriendshipMapper.insertFunFriendship(funFriendship);
    }

    /**
     * 修改用户好友关系
     * 
     * @param funFriendship 用户好友关系
     * @return 结果
     */
    @Override
    public int updateFunFriendship(FunFriendship funFriendship)
    {
        return funFriendshipMapper.updateFunFriendship(funFriendship);
    }

    /**
     * 批量删除用户好友关系
     * 
     * @param friendshipIds 需要删除的用户好友关系主键
     * @return 结果
     */
    @Override
    public int deleteFunFriendshipByFriendshipIds(String[] friendshipIds)
    {
        return funFriendshipMapper.deleteFunFriendshipByFriendshipIds(friendshipIds);
    }

    /**
     * 删除用户好友关系信息
     * 
     * @param friendshipId 用户好友关系主键
     * @return 结果
     */
    @Override
    public int deleteFunFriendshipByFriendshipId(String friendshipId)
    {
        return funFriendshipMapper.deleteFunFriendshipByFriendshipId(friendshipId);
    }
}
