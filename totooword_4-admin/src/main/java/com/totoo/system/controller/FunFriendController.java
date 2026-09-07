package com.totoo.system.controller;

import java.util.*;
import javax.servlet.FilterRegistration;
import javax.servlet.http.HttpServletResponse;

import com.totoo.common.core.domain.entity.SysUser;
import com.totoo.system.domain.FunChatMessage;
import com.totoo.system.service.IFunChatMessageService;
import com.totoo.system.service.ISysUserService;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.totoo.common.annotation.Log;
import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.common.enums.BusinessType;
import com.totoo.system.domain.FunFriend;
import com.totoo.system.service.IFunFriendService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 好友关系Controller
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/system/friend")
public class FunFriendController extends BaseController
{
    @Autowired
    private IFunFriendService funFriendService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IFunChatMessageService chatMessageService;

    /**
     * 查询好友关系列表
     */
//    @PreAuthorize("@ss.hasPermi('system:friend:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunFriend funFriend)
    {
        startPage();
        List<FunFriend> list = funFriendService.selectFunFriendList(funFriend);
        return getDataTable(list);
    }

    @GetMapping("/listBySelf")
    public AjaxResult listBySelf(){//返回passed->已通过的好友 awaited->等待别人处理 needToBeProcessed->处理别人
        HashMap<String,List<FunFriend>> rst = new HashMap<>();
        rst.put("passed",new ArrayList<>()); rst.put("awaited",new ArrayList<>()); rst.put("needToBeProcessed",new ArrayList<>());
        FunFriend funFriend = new FunFriend();List<FunFriend> Friends=new ArrayList<>();
        funFriend.setUserId1(getUserId());Friends.addAll( funFriendService.selectFunFriendList(funFriend));funFriend.setUserId1(null);//装入主动[加的/申请]好友
        funFriend.setUserId2(getUserId());Friends.addAll(funFriendService.selectFunFriendList(funFriend));//装入被[加的/申请]好友
        for(FunFriend friend:Friends){//按状态分类加入rst
            switch (friend.getStatus()){
                case "0":
                    if(friend.getUserId1().equals(getUserId()))rst.get("awaited").add(friend);//自己主动加的
                    else rst.get("needToBeProcessed").add(friend);//别人主动加的
                    break;
                case "1":rst.get("passed").add(friend);break;
            }
        }
        return success(rst);
    }
    @GetMapping("/getFriendList")
    public AjaxResult getFriendList(){//返回passed needToBeProcessed awaited对应的用户
        Map<String,List<FunFriend>> rst = (Map<String, List<FunFriend>>) listBySelf().get(AjaxResult.DATA_TAG);
        List<FunFriend> passedFriends = rst.get("passed"), needToBeProcessedFriends = rst.get("needToBeProcessed"), awaitedFriends = rst.get("awaited");
        List< SysUser>passedUsers = new ArrayList<>(), needToBeProcessedUsers = new ArrayList<>(), awaitedUsers = new ArrayList<>();
        for (FunFriend friend : passedFriends) {
            SysUser user = userService.selectUserById(Objects.equals(friend.getUserId1(), getUserId()) ?friend.getUserId2():friend.getUserId1());
            user.clearSensitiveInfo();
            passedUsers.add(user);
        }
        for( FunFriend friend : needToBeProcessedFriends) {
            SysUser user = userService.selectUserById(friend.getUserId1());
            user.clearSensitiveInfo();
            needToBeProcessedUsers.add(user);
        }
        for( FunFriend friend : awaitedFriends) {
            SysUser user = userService.selectUserById(friend.getUserId2());
            user.clearSensitiveInfo();
            awaitedUsers.add(user);
        }
        return success(new HashMap<String,List<SysUser>>(){{
put("passed",passedUsers); put("needToBeProcessed",needToBeProcessedUsers); put("awaited",awaitedUsers);
        }});
    }

    /**
     * 导出好友关系列表
     */
//    @PreAuthorize("@ss.hasPermi('system:friend:export')")
    @Log(title = "好友关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunFriend funFriend)
    {
        List<FunFriend> list = funFriendService.selectFunFriendList(funFriend);
        ExcelUtil<FunFriend> util = new ExcelUtil<FunFriend>(FunFriend.class);
        util.exportExcel(response, list, "好友关系数据");
    }

    /**
     * 获取好友关系详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:friend:query')")
    @GetMapping(value = "/{friendId}")
    public AjaxResult getInfo(@PathVariable("friendId") Long friendId)
    {
        return success(funFriendService.selectFunFriendByFriendId(friendId));
    }

    /**
     * 新增好友关系
     */
//    @PreAuthorize("@ss.hasPermi('system:friend:add')")
    @Log(title = "好友关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunFriend funFriend)
    {
        funFriend.setUserId1(getUserId());funFriend.setStatus("0");
        funFriend.setActionTime(new Date());
        funFriendService.insertFunFriend(funFriend);
        return AjaxResult.success(funFriend);
    }

    /**
     * 修改好友关系
     */
//    @PreAuthorize("@ss.hasPermi('system:friend:edit')")
    @Log(title = "好友关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunFriend funFriend)
    {
        return toAjax(funFriendService.updateFunFriend(funFriend));
    }

    /**
     * 删除好友关系
     */
//    @PreAuthorize("@ss.hasPermi('system:friend:remove')")
    @Log(title = "好友关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{friendIds}")
    public AjaxResult remove(@PathVariable Long[] friendIds)
    {
        return toAjax(funFriendService.deleteFunFriendByFriendIds(friendIds));
    }
    @GetMapping("/getBySelfAndOtherUserId")
    public AjaxResult getBySelfAndOtherUserId(@RequestParam Long userId){
        FunFriend funFriend = new FunFriend();
        {
            funFriend.setUserId1(getUserId());
            funFriend.setUserId2(userId);
            List<FunFriend> rst = funFriendService.selectFunFriendList(funFriend);
            if (rst.size() == 1) return AjaxResult.success(rst.get(0));
        }
        {
            funFriend.setUserId2(getUserId());
            funFriend.setUserId1(userId);
            List<FunFriend> rst = funFriendService.selectFunFriendList(funFriend);
            if (rst.size() == 1) return AjaxResult.success(rst.get(0));
        }
        return AjaxResult.error( "没有找到好友关系");
    }

    @PostMapping("/agreeFriend")
    public AjaxResult agreeFriend(@RequestBody FunFriend funFriend){
        funFriend.setUserId2(getUserId());
        funFriend=funFriendService.selectFunFriendList(funFriend).get(0);
        System.out.println(">>>funFriend>>>"+funFriend);
        funFriend.setStatus("1");
        funFriendService.updateFunFriend(funFriend);
        return AjaxResult.success( );
    }
    @PostMapping("/refuseFriend")
    public AjaxResult refuseFriend(@RequestBody FunFriend funFriend){
        funFriend.setUserId2(getUserId());
        funFriend=funFriendService.selectFunFriendList(funFriend).get(0);
        System.out.println(">>>funFriend>>>"+funFriend);
        funFriend.setStatus("2");
        funFriendService.updateFunFriend(funFriend);
        return AjaxResult.success( );
    }

}
