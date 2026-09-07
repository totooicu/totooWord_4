package com.totoo.system.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.totoo.common.core.domain.entity.SysUser;
import com.totoo.system.domain.FunFriend;
import com.totoo.system.domain.FunGroup;
import com.totoo.system.domain.FunGroupMember;
import com.totoo.system.service.IFunFriendService;
import com.totoo.system.service.IFunGroupMemberService;
import com.totoo.system.service.IFunGroupService;
import com.totoo.system.service.impl.SysUserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.totoo.common.annotation.Log;
import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.common.enums.BusinessType;
import com.totoo.system.domain.FunChatMessage;
import com.totoo.system.service.IFunChatMessageService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 聊天消息Controller
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/system/message")
public class FunChatMessageController extends BaseController
{
    @Autowired
    private IFunChatMessageService funChatMessageService;
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    IFunGroupService funGroupService;
    @Autowired
    IFunGroupMemberService funGroupMemberService;
    @Autowired
    private IFunFriendService funFriendService;

    /**
     * 查询聊天消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunChatMessage funChatMessage)
    {
        startPage();
        List<FunChatMessage> list = funChatMessageService.selectFunChatMessageList(funChatMessage);
        return getDataTable(list);
    }

    /**
     * 导出聊天消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:message:export')")
    @Log(title = "聊天消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunChatMessage funChatMessage)
    {
        List<FunChatMessage> list = funChatMessageService.selectFunChatMessageList(funChatMessage);
        ExcelUtil<FunChatMessage> util = new ExcelUtil<FunChatMessage>(FunChatMessage.class);
        util.exportExcel(response, list, "聊天消息数据");
    }

    /**
     * 获取聊天消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:message:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId)
    {
        return success(funChatMessageService.selectFunChatMessageByMessageId(messageId));
    }

    /**
     * 新增聊天消息
     */
    @PreAuthorize("@ss.hasPermi('system:message:add')")
    @Log(title = "聊天消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunChatMessage funChatMessage)
    {
        return toAjax(funChatMessageService.insertFunChatMessage(funChatMessage));
    }

    /**
     * 修改聊天消息
     */
    @PreAuthorize("@ss.hasPermi('system:message:edit')")
    @Log(title = "聊天消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunChatMessage funChatMessage)
    {
        return toAjax(funChatMessageService.updateFunChatMessage(funChatMessage));
    }

    /**
     * 删除聊天消息
     */
    @PreAuthorize("@ss.hasPermi('system:message:remove')")
    @Log(title = "聊天消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds)
    {
        return toAjax(funChatMessageService.deleteFunChatMessageByMessageIds(messageIds));
    }
    @PostMapping("sendMessage")
    public AjaxResult sendMessage(@RequestBody FunChatMessage funChatMessage){
        funChatMessage.setSenderId( getUserId());
        funChatMessage.setSendTime(new Date());
        System.out.println(">>>FunChatMessageController.sendMessage: "+funChatMessage);
        funChatMessageService.insertFunChatMessage(funChatMessage);
        funChatMessage.fillOtherInfoByService(sysUserService,funGroupService,funGroupMemberService,funFriendService,null);
        return AjaxResult.success(funChatMessage);
    }
    @PostMapping("/listByChatMessageBySelfUserIdOrGroupId")
    public AjaxResult listByChatMessageBySelfUserIdOrGroupId(@RequestBody FunChatMessage funChatMessage){//除了返回消息外还要返回涉及到的成员
        Long otherUserId = funChatMessage.getReceiverId();
        HashMap<String, List<Object>> map = new HashMap<>();
        map.put("messages",new ArrayList<>());map.put("otherUsers",new ArrayList<>());map.put("self",new ArrayList<>());map.put("groups",new ArrayList<>());
        List<FunChatMessage> list=new ArrayList<>();
        if(Objects.equals(funChatMessage.getMessageType(), "0")) {//处理好友之间
            funChatMessage.setSenderId(getUserId());
            list = funChatMessageService.selectFunChatMessageList(funChatMessage);//处理messages
            funChatMessage.setSenderId(otherUserId);funChatMessage.setReceiverId(getUserId());}
            list.addAll(funChatMessageService.selectFunChatMessageList(funChatMessage));


        list.sort((o1, o2)-> o1.getSendTime().compareTo(o2.getSendTime()));
//        for (FunChatMessage message : list) message.fillOtherInfoByService(sysUserService,funGroupService,funGroupMemberService);
        map.get("messages").addAll(list);
        //处理self
        map.get("self").add( sysUserService.selectUserById(getUserId()).clearSensitiveInfo());
        //处理otherUsers
        System.out.println(">>>FunChatMessageController.listByChatMessageBySelfUserIdOrGroupId: 好友之间: MessageType: "+funChatMessage.getMessageType());
        if(Objects.equals(funChatMessage.getMessageType(), "0")){//处理好友之间
            System.out.println(">>>FunChatMessageController.listByChatMessageBySelfUserIdOrGroupId: 好友之间: 对方Id"+otherUserId);
            map.get("otherUsers").add(sysUserService.selectUserById(otherUserId).clearSensitiveInfo());
            return AjaxResult.success(map);
        }//处理群聊
        Set<Long> userIds = new HashSet<>();
        for (FunChatMessage message : list) userIds.add(message.getSenderId());
        userIds.remove(getUserId()); //减去自身id
        for(Long userId:userIds)map.get("otherUsers").add(sysUserService.selectUserById(userId).clearSensitiveInfo());
        map.get("groups").add(funGroupService.selectFunGroupByGroupId(funChatMessage.getGroupId()));//处理groups
        return AjaxResult.success(map);
    }

    @PutMapping("/watched")
    public AjaxResult watched(@RequestBody FunChatMessage chatMessage) {
        FunChatMessage lastMsg = new FunChatMessage();
        lastMsg = funChatMessageService.selectLast(lastMsg);//获取最后一条消息
        Long lastMsgId = lastMsg.getMessageId();
        if (Objects.equals(chatMessage.getMessageType(), "0")) {//处理好友消息
            FunFriend funFriend = new FunFriend();
            funFriend.setUserId1(getUserId());
            funFriend.setUserId2(chatMessage.getReceiverId());
            List<FunFriend> rst = funFriendService.selectFunFriendList(funFriend);
            if (rst.size() == 1) funFriend = rst.get(0);
            else {
                funFriend.setUserId1(chatMessage.getReceiverId());
                funFriend.setUserId2(getUserId());
                funFriend = funFriendService.selectFunFriendList(funFriend).get(0);
            }
            funFriend.setLatestMessageId(lastMsgId);
            funFriendService.updateFunFriend(funFriend);
        } else {//处理群消息
            FunGroupMember funGroupMember = new FunGroupMember();
            funGroupMember.setGroupId(chatMessage.getGroupId());
            funGroupMember.setUserId(getUserId());
            funGroupMember = funGroupMemberService.selectFunGroupMemberList(funGroupMember).get(0);
            funGroupMember.setLatestMessageId(lastMsgId);
            funGroupMemberService.updateFunGroupMember(funGroupMember);
        }
        return AjaxResult.success(lastMsgId);
    }
}
