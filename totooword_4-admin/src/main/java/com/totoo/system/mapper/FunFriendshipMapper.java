package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunFriendship;

/**
 * 用户好友关系Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public interface FunFriendshipMapper 
{
    /**
     * 查询用户好友关系
     * 
     * @param friendshipId 用户好友关系主键
     * @return 用户好友关系
     */
    public FunFriendship selectFunFriendshipByFriendshipId(String friendshipId);

    /**
     * 查询用户好友关系列表
     * 
     * @param funFriendship 用户好友关系
     * @return 用户好友关系集合
     */
    public List<FunFriendship> selectFunFriendshipList(FunFriendship funFriendship);

    /**
     * 新增用户好友关系
     * 
     * @param funFriendship 用户好友关系
     * @return 结果
     */
    public int insertFunFriendship(FunFriendship funFriendship);

    /**
     * 修改用户好友关系
     * 
     * @param funFriendship 用户好友关系
     * @return 结果
     */
    public int updateFunFriendship(FunFriendship funFriendship);

    /**
     * 删除用户好友关系
     * 
     * @param friendshipId 用户好友关系主键
     * @return 结果
     */
    public int deleteFunFriendshipByFriendshipId(String friendshipId);

    /**
     * 批量删除用户好友关系
     * 
     * @param friendshipIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunFriendshipByFriendshipIds(String[] friendshipIds);
}
