package com.totoo.system.service;

import java.util.List;
import com.totoo.system.domain.FunChatMessage;
import com.totoo.system.domain.FunGroup;

/**
 * 聊天消息Service接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface IFunChatMessageService 
{
    /**
     * 查询聊天消息
     * 
     * @param messageId 聊天消息主键
     * @return 聊天消息
     */
    public FunChatMessage selectFunChatMessageByMessageId(Long messageId);

    /**
     * 查询聊天消息列表
     * 
     * @param funChatMessage 聊天消息
     * @return 聊天消息集合
     */
    public List<FunChatMessage> selectFunChatMessageList(FunChatMessage funChatMessage);

    /**
     * 新增聊天消息
     * 
     * @param funChatMessage 聊天消息
     * @return 结果
     */
    public int insertFunChatMessage(FunChatMessage funChatMessage);

    /**
     * 修改聊天消息
     * 
     * @param funChatMessage 聊天消息
     * @return 结果
     */
    public int updateFunChatMessage(FunChatMessage funChatMessage);

    /**
     * 批量删除聊天消息
     * 
     * @param messageIds 需要删除的聊天消息主键集合
     * @return 结果
     */
    public int deleteFunChatMessageByMessageIds(Long[] messageIds);

    /**
     * 删除聊天消息信息
     * 
     * @param messageId 聊天消息主键
     * @return 结果
     */
    public int deleteFunChatMessageByMessageId(Long messageId);

    List<FunChatMessage> selectFunChatMessageListByUserId(Long userId);

    List<FunChatMessage> selectFunChatMessageListByGroupsOrGroupIds(List<Long> groupIds, List<FunGroup> groups);

    List<FunChatMessage> filterMaxMessageId(List<FunChatMessage> messages);


    // 获取用户或群组最后一条消息
    FunChatMessage getLastMessageByChatMessageByUserIdOrGroupId(FunChatMessage funChatMessage);

    FunChatMessage selectLast(FunChatMessage funChatMessage);
}
