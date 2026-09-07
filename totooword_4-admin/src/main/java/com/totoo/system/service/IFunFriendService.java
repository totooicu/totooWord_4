package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunFriend;

/**
 * 好友关系Service接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface IFunFriendService 
{
    /**
     * 查询好友关系
     * 
     * @param friendId 好友关系主键
     * @return 好友关系
     */
    public FunFriend selectFunFriendByFriendId(Long friendId);

    /**
     * 查询好友关系列表
     * 
     * @param funFriend 好友关系
     * @return 好友关系集合
     */
    public List<FunFriend> selectFunFriendList(FunFriend funFriend);

    /**
     * 新增好友关系
     * 
     * @param funFriend 好友关系
     * @return 结果
     */
    public int insertFunFriend(FunFriend funFriend);

    /**
     * 修改好友关系
     * 
     * @param funFriend 好友关系
     * @return 结果
     */
    public int updateFunFriend(FunFriend funFriend);

    /**
     * 批量删除好友关系
     * 
     * @param friendIds 需要删除的好友关系主键集合
     * @return 结果
     */
    public int deleteFunFriendByFriendIds(Long[] friendIds);

    /**
     * 删除好友关系信息
     * 
     * @param friendId 好友关系主键
     * @return 结果
     */
    public int deleteFunFriendByFriendId(Long friendId);

    List<FunFriend> selectFunFriendListByUserId(Long userId);
}
