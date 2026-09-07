package com.totoo.system.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.totoo.common.core.domain.entity.SysUser;
import com.totoo.system.service.IFunFriendService;
import com.totoo.system.service.IFunGroupMemberService;
import com.totoo.system.service.IFunGroupService;
import com.totoo.system.service.ISysUserService;
import com.totoo.system.service.impl.SysUserServiceImpl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.totoo.common.annotation.Excel;
import com.totoo.common.core.domain.BaseEntity;

/**
 * 聊天消息对象 fun_chat_message
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public class FunChatMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    public SysUser getSender() {
        return sender;
    }

    public void setSender(SysUser sender) {
        this.sender = sender;
    }

    public SysUser getReceiver() {
        return receiver;
    }

    public void setReceiver(SysUser receiver) {
        this.receiver = receiver;
    }

    public FunGroup getGroup() {
        return group;
    }

    public void setGroup(FunGroup group) {
        this.group = group;
    }

    /** 发送者用户ID */
    @Excel(name = "发送者用户ID")
    private Long senderId;
    private SysUser sender;

    /** 接收者用户ID（用于私聊） */
    @Excel(name = "接收者用户ID", readConverterExp = "用=于私聊")
    private Long receiverId;
    private SysUser receiver;
    private FunFriend friend;

    @Override
    public String toString() {
        return "FunChatMessage{" +
                "messageId=" + messageId +
                ", senderId=" + senderId +
                ", sender=" + sender +
                ", receiverId=" + receiverId +
                ", receiver=" + receiver +
                ", friend=" + friend +
                ", groupId=" + groupId +
                ", group=" + group +
                ", groupMember=" + groupMember +
                ", messageType='" + messageType + '\'' +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunChatMessage that = (FunChatMessage) o;
        return Objects.equals(messageId, that.messageId) && Objects.equals(senderId, that.senderId) && Objects.equals(sender, that.sender) && Objects.equals(receiverId, that.receiverId) && Objects.equals(receiver, that.receiver) && Objects.equals(friend, that.friend) && Objects.equals(groupId, that.groupId) && Objects.equals(group, that.group) && Objects.equals(groupMember, that.groupMember) && Objects.equals(messageType, that.messageType) && Objects.equals(content, that.content) && Objects.equals(sendTime, that.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, senderId, sender, receiverId, receiver, friend, groupId, group, groupMember, messageType, content, sendTime);
    }

    public FunFriend getFriend() {
        return friend;
    }

    public void setFriend(FunFriend friend) {
        this.friend = friend;
    }

    public FunGroupMember getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(FunGroupMember groupMember) {
        this.groupMember = groupMember;
    }

    /** 群组ID（用于群聊） */
    @Excel(name = "群组ID", readConverterExp = "用=于群聊")
    private Long groupId;
    private FunGroup group;
    /** 消息类型（0好友私聊消息，1群聊消息，2已读的私聊消息） */
    private FunGroupMember groupMember;
    @Excel(name = "消息类型", readConverterExp = "0=好友私聊消息，1群聊消息，2已读的私聊消息")
    private String messageType;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    /** 发送时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }
    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
    }
    public void fillOtherInfoByService(ISysUserService sysUserService, IFunGroupService funGroupService, IFunGroupMemberService funGroupMemberService, IFunFriendService funFriendService, Long groupUserId) {
        sender = sysUserService.selectUserById(senderId);
        sender.clearSensitiveInfo();
        if(Objects.equals(messageType, "0")) {
            receiver = sysUserService.selectUserById(receiverId);
            receiver.clearSensitiveInfo();
            friend=new FunFriend();
            friend.setUserId1(senderId);friend.setUserId2(receiverId);
            List<FunFriend> rst = funFriendService.selectFunFriendList(friend);
            if(rst.size()==1)friend=rst.get(0);
            else{
                friend.setUserId1(receiverId);friend.setUserId2(senderId);
                rst=funFriendService.selectFunFriendList(friend);
                if(rst.size()==1)friend=rst.get(0);
            }
        }
        else{
            group = funGroupService.selectFunGroupByGroupId(groupId);
            FunGroupMember funGroupMember = new FunGroupMember();
            funGroupMember.setGroupId(groupId); funGroupMember.setUserId(groupUserId!=null?groupUserId: senderId);
            System.out.println(">>>FunChatMessage.fillOtherInfoByService: " + groupId+ ", " + senderId);
            List<FunGroupMember> funGroupMembers = funGroupMemberService.selectFunGroupMemberList(funGroupMember);
            groupMember = funGroupMembers.get(0);
            }
    }
}
