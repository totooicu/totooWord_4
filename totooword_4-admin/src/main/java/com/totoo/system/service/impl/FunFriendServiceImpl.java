package com.totoo.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunFriendMapper;
import com.totoo.system.domain.FunFriend;
import com.totoo.system.service.IFunFriendService;

/**
 * 好友关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class FunFriendServiceImpl implements IFunFriendService 
{
    @Autowired
    private FunFriendMapper funFriendMapper;

    /**
     * 查询好友关系
     * 
     * @param friendId 好友关系主键
     * @return 好友关系
     */
    @Override
    public FunFriend selectFunFriendByFriendId(Long friendId)
    {
        return funFriendMapper.selectFunFriendByFriendId(friendId);
    }

    /**
     * 查询好友关系列表
     * 
     * @param funFriend 好友关系
     * @return 好友关系
     */
    @Override
    public List<FunFriend> selectFunFriendList(FunFriend funFriend)
    {
        return funFriendMapper.selectFunFriendList(funFriend);
    }

    /**
     * 新增好友关系
     * 
     * @param funFriend 好友关系
     * @return 结果
     */
    @Override
    public int insertFunFriend(FunFriend funFriend)
    {
        return funFriendMapper.insertFunFriend(funFriend);
    }

    /**
     * 修改好友关系
     * 
     * @param funFriend 好友关系
     * @return 结果
     */
    @Override
    public int updateFunFriend(FunFriend funFriend)
    {
        return funFriendMapper.updateFunFriend(funFriend);
    }

    /**
     * 批量删除好友关系
     * 
     * @param friendIds 需要删除的好友关系主键
     * @return 结果
     */
    @Override
    public int deleteFunFriendByFriendIds(Long[] friendIds)
    {
        return funFriendMapper.deleteFunFriendByFriendIds(friendIds);
    }

    /**
     * 删除好友关系信息
     * 
     * @param friendId 好友关系主键
     * @return 结果
     */
    @Override
    public int deleteFunFriendByFriendId(Long friendId)
    {
        return funFriendMapper.deleteFunFriendByFriendId(friendId);
    }
    @Override
    public List<FunFriend> selectFunFriendListByUserId(Long userId) {
        FunFriend funFriend = new FunFriend();
        funFriend.setUserId1(userId);
        List<FunFriend> funFriends = new ArrayList<>(funFriendMapper.selectFunFriendList(funFriend));
        funFriend.setUserId2(userId);funFriend.setUserId1(null);
        funFriends.addAll(funFriendMapper.selectFunFriendList(funFriend));
        return funFriends;
    }
}
