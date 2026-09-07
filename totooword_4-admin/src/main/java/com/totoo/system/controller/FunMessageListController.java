package com.totoo.system.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.totoo.common.core.domain.entity.SysUser;
import com.totoo.system.domain.*;
import com.totoo.system.service.*;
//import org.springframework.security.access.prepost.PreAuthorize;
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
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 消息列Controller
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/system/messageList")
public class FunMessageListController extends BaseController
{
    @Autowired
    private IFunMessageListService funMessageListService;
    @Autowired
    private IFunChatMessageService funChatMessageService;
    @Autowired
    private ISysUserService funUserService;
    @Autowired
    private IFunGroupService funGroupService;
    @Autowired
    private IFunGroupMemberService funGroupMemberService;
    @Autowired
    private IFunFriendService funFriendService;

    /**
     * 查询消息列列表
     */
//    @PreAuthorize("@ss.hasPermi('system:list:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunMessageList funMessageList)
    {
        startPage();
        List<FunMessageList> list = funMessageListService.selectFunMessageListList(funMessageList);
        return getDataTable(list);
    }

    /**
     * 导出消息列列表
     */
//    @PreAuthorize("@ss.hasPermi('system:list:export')")
    @Log(title = "消息列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunMessageList funMessageList)
    {
        List<FunMessageList> list = funMessageListService.selectFunMessageListList(funMessageList);
        ExcelUtil<FunMessageList> util = new ExcelUtil<FunMessageList>(FunMessageList.class);
        util.exportExcel(response, list, "消息列数据");
    }

    /**
     * 获取消息列详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:list:query')")
    @GetMapping(value = "/{listId}")
    public AjaxResult getInfo(@PathVariable("listId") Long listId)
    {
        return success(funMessageListService.selectFunMessageListByListId(listId));
    }

    /**
     * 新增消息列
     */
//    @PreAuthorize("@ss.hasPermi('system:list:add')")
    @Log(title = "消息列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunMessageList funMessageList)
    {
        return toAjax(funMessageListService.insertFunMessageList(funMessageList));
    }

    /**
     * 修改消息列
     */
//    @PreAuthorize("@ss.hasPermi('system:list:edit')")
    @Log(title = "消息列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunMessageList funMessageList)
    {
        return toAjax(funMessageListService.updateFunMessageList(funMessageList));
    }

    /**
     * 删除消息列
     */
//    @PreAuthorize("@ss.hasPermi('system:list:remove')")
    @Log(title = "消息列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{listIds}")
    public AjaxResult remove(@PathVariable Long[] listIds)
    {
        return toAjax(funMessageListService.deleteFunMessageListByListIds(listIds));
    }
    //获取用户好友->获取有关好友的聊天最大消息列表->获取好友信息
    //获取加入到哪些群（群成员列表）->获取群最大消息列表->获取群信息
    @GetMapping("/getMessageList")
    public AjaxResult getMessageList(){//返回好友/群组（FunChatMessage.[sender/receiver/Group]），并带有最后一条消息(FunChatMessage)，以及判断是否来了新的消息(FunChatMessage.[Friend/FunGroupMember].latestMessageId)
        List<FunChatMessage> chatMessageList = new ArrayList<>();
        List<FunGroup> groups = funGroupService.witchGroupIn(getUserId());//加入了哪些群聊
        for(FunGroup group:groups) {//添加群的最新消息
            FunChatMessage chatMessage = new FunChatMessage();chatMessage.setGroupId(group.getGroupId());chatMessage.setMessageType("1");
            System.out.println(">>>group2:"+funChatMessageService.getLastMessageByChatMessageByUserIdOrGroupId(chatMessage));
            chatMessageList.add(funChatMessageService.getLastMessageByChatMessageByUserIdOrGroupId(chatMessage));
        }
        System.out.println(">>>group:"+groups);
        System.out.println(">>>chatMessageList:"+chatMessageList);
        List<FunFriend> friends = funFriendService.selectFunFriendListByUserId(getUserId());//获取我的好友
        System.out.println(">>>friends:"+friends);
        for(FunFriend friend:friends) {//添加好友最新消息
            FunChatMessage chatMessage = new FunChatMessage();chatMessage.setSenderId(friend.getUserId1());chatMessage.setReceiverId(friend.getUserId2());chatMessage.setMessageType("0");
            chatMessageList.add(funChatMessageService.getLastMessageByChatMessageByUserIdOrGroupId(chatMessage));
        }
        chatMessageList.removeIf(Objects::isNull); //将空元素都删除
        System.out.println(">>>chatMessageList:"+chatMessageList);
        for(FunChatMessage funChatMessage:chatMessageList)funChatMessage.fillOtherInfoByService(funUserService,funGroupService,funGroupMemberService,funFriendService,getUserId());//获取信息
        chatMessageList.sort((o1, o2) -> { return o2.getMessageId().compareTo(o1.getMessageId());});//排序
        return AjaxResult.success(chatMessageList);
    }

    @GetMapping("/getMessageList1")
    public AjaxResult getMessageList1(){//返回消息列表
        FunChatMessage funChatMessage = new FunChatMessage();
        //分别搜索senderId和receiverId为userId的聊天记录
        ArrayList<FunChatMessage> chatMessageList = new ArrayList<>(funChatMessageService.selectFunChatMessageListByUserId(getUserId()));
        //获取好友id集合
        Set<Long>userIds = new HashSet<>();
        for (FunChatMessage chatMessage:chatMessageList){userIds.add(chatMessage.getSenderId());userIds.add(chatMessage.getReceiverId());}
        List<FunGroup> groups = funGroupService.witchGroupIn(getUserId());//加入了哪些群聊
        for(FunGroup group:groups) {//添加群的最新消息
            FunChatMessage chatMessage = new FunChatMessage();chatMessage.setGroupId(group.getGroupId());chatMessage.setMessageType("1");
            System.out.println(">>>group2:"+funChatMessageService.getLastMessageByChatMessageByUserIdOrGroupId(chatMessage));
            chatMessageList.add(funChatMessageService.getLastMessageByChatMessageByUserIdOrGroupId(chatMessage));
        }



        return AjaxResult.success(chatMessageList);
    }
}
