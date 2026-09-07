package com.totoo.system.mapper;

import java.util.List;
import com.totoo.system.domain.FunChatMessage;

/**
 * 聊天消息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface FunChatMessageMapper 
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
     * 删除聊天消息
     * 
     * @param messageId 聊天消息主键
     * @return 结果
     */
    public int deleteFunChatMessageByMessageId(Long messageId);

    /**
     * 批量删除聊天消息
     * 
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFunChatMessageByMessageIds(Long[] messageIds);

    List<FunChatMessage> selectFunChatMessageListByUserId(Long userId);
    public FunChatMessage getLastMessageByChatMessageByUserIdOrGroupId(FunChatMessage funChatMessage);
    public FunChatMessage selectFunChatMessageLast(FunChatMessage funChatMessage);
}
