package com.totoo.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.totoo.system.domain.FunGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunChatMessageMapper;
import com.totoo.system.domain.FunChatMessage;
import com.totoo.system.service.IFunChatMessageService;

/**
 * 聊天消息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class FunChatMessageServiceImpl implements IFunChatMessageService 
{
    @Autowired
    private FunChatMessageMapper funChatMessageMapper;

    /**
     * 查询聊天消息
     * 
     * @param messageId 聊天消息主键
     * @return 聊天消息
     */
    @Override
    public FunChatMessage selectFunChatMessageByMessageId(Long messageId)
    {
        return funChatMessageMapper.selectFunChatMessageByMessageId(messageId);
    }

    /**
     * 查询聊天消息列表
     * 
     * @param funChatMessage 聊天消息
     * @return 聊天消息
     */
    @Override
    public List<FunChatMessage> selectFunChatMessageList(FunChatMessage funChatMessage)
    {
        return funChatMessageMapper.selectFunChatMessageList(funChatMessage);
    }

    /**
     * 新增聊天消息
     * 
     * @param funChatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int insertFunChatMessage(FunChatMessage funChatMessage)
    {
        return funChatMessageMapper.insertFunChatMessage(funChatMessage);
    }

    /**
     * 修改聊天消息
     * 
     * @param funChatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int updateFunChatMessage(FunChatMessage funChatMessage)
    {
        return funChatMessageMapper.updateFunChatMessage(funChatMessage);
    }

    /**
     * 批量删除聊天消息
     * 
     * @param messageIds 需要删除的聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteFunChatMessageByMessageIds(Long[] messageIds)
    {
        return funChatMessageMapper.deleteFunChatMessageByMessageIds(messageIds);
    }

    /**
     * 删除聊天消息信息
     * 
     * @param messageId 聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteFunChatMessageByMessageId(Long messageId)
    {
        return funChatMessageMapper.deleteFunChatMessageByMessageId(messageId);
    }
    @Override
    public List<FunChatMessage> selectFunChatMessageListByUserId(Long userId)
    {
        return funChatMessageMapper.selectFunChatMessageListByUserId(userId);
    }
    @Override
    public List<FunChatMessage> selectFunChatMessageListByGroupsOrGroupIds(List<Long> groupIds, List<FunGroup> groups)
    {
        if(groupIds==null)groupIds=new ArrayList<>();
        groupIds.addAll(groups.stream().map(FunGroup::getGroupId).collect(Collectors.toList()));
        List<FunChatMessage> messages = new ArrayList<>();
        for(Long groupId:groupIds){
            FunChatMessage funChatMessage=new FunChatMessage();
            funChatMessage.setGroupId(groupId);
            messages.addAll(funChatMessageMapper.selectFunChatMessageList(funChatMessage));
        }
        return messages;
    }
    @Override
    public List<FunChatMessage> filterMaxMessageId(List<FunChatMessage> messages) {
        // 使用Collectors.toMap来收集具有相同senderId、receiverId和groupId的消息
        Map<String, FunChatMessage> messageMap = messages.stream()
                .collect(Collectors.toMap(
                        m ->(m.getMessageType().equals("0")?( m.getSenderId() + "-" + m.getReceiverId() ): String.valueOf( m.getGroupId())),
                        Function.identity(),
                        (m1, m2) -> m1.getMessageId() > m2.getMessageId() ? m1 : m2)
                );

        // 返回Map的值，即保留了messageId最大的消息
        return new ArrayList<>(messageMap.values());
    }
    // 获取用户或群组最后一条消息
    @Override
    public FunChatMessage getLastMessageByChatMessageByUserIdOrGroupId(FunChatMessage funChatMessage) {
        if(Objects.equals(funChatMessage.getMessageType(), "0")){
            FunChatMessage funChatMessage1 = new FunChatMessage();
            funChatMessage1.setReceiverId(funChatMessage.getSenderId());funChatMessage1.setSenderId(funChatMessage.getReceiverId());funChatMessage1.setMessageType("0");
            funChatMessage1= selectLast(funChatMessage1);
            funChatMessage=selectLast(funChatMessage);
            //两个都空返回null，两个都不空返回messageId最大的，一个空返回另一个
            if(funChatMessage==null&&funChatMessage1==null)return null;
            if(funChatMessage==null)return funChatMessage1;
            if(funChatMessage1==null)return funChatMessage;
            return funChatMessage.getMessageId()>funChatMessage1.getMessageId()?funChatMessage:funChatMessage1;
        }else{
            return selectLast(funChatMessage);
        }
    }
    @Override
    public FunChatMessage selectLast(FunChatMessage funChatMessage){
        return funChatMessageMapper.selectFunChatMessageLast(funChatMessage);
    }
}
