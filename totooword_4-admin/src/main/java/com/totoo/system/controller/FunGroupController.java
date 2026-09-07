package com.totoo.system.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.totoo.common.core.domain.entity.SysUser;
import com.totoo.system.domain.FunGroupMember;
import com.totoo.system.service.IFunGroupMemberService;
import com.totoo.system.service.ISysUserService;
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
import com.totoo.system.domain.FunGroup;
import com.totoo.system.service.IFunGroupService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 群组Controller
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/system/group")
public class FunGroupController extends BaseController
{
    @Autowired
    private IFunGroupService funGroupService;
    @Autowired
    private IFunGroupMemberService funGroupMemberService;
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    IFunGroupMemberService groupMemberService;
    @Autowired
    IFunGroupService groupService;
    /**
     * 查询群组列表
     */
    @PreAuthorize("@ss.hasPermi('system:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunGroup funGroup)
    {
        startPage();
        List<FunGroup> list = funGroupService.selectFunGroupList(funGroup);
        return getDataTable(list);
    }

    /**
     * 导出群组列表
     */
    @PreAuthorize("@ss.hasPermi('system:group:export')")
    @Log(title = "群组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunGroup funGroup)
    {
        List<FunGroup> list = funGroupService.selectFunGroupList(funGroup);
        ExcelUtil<FunGroup> util = new ExcelUtil<FunGroup>(FunGroup.class);
        util.exportExcel(response, list, "群组数据");
    }

    /**
     * 获取群组详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return success(funGroupService.selectFunGroupByGroupId(groupId));
    }

    /**
     * 新增群组
     */
    @PreAuthorize("@ss.hasPermi('system:group:add')")
    @Log(title = "群组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunGroup funGroup)
    {
        return toAjax(funGroupService.insertFunGroup(funGroup));
    }

    /**
     * 修改群组
     */
    @PreAuthorize("@ss.hasPermi('system:group:edit')")
    @Log(title = "群组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunGroup funGroup)
    {
        return toAjax(funGroupService.updateFunGroup(funGroup));
    }

    /**
     * 删除群组
     */
    @PreAuthorize("@ss.hasPermi('system:group:remove')")
    @Log(title = "群组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(funGroupService.deleteFunGroupByGroupIds(groupIds));
    }
    @GetMapping("/getGroupList")
    public AjaxResult getGroupList(){

        List<FunGroupMember> list = funGroupMemberService.selectFunGroupMemberListByUserId(getUserId());
        HashMap<String,List<Object>> map = new HashMap<>();
        map.put("passed",new ArrayList<>());map.put("awaited",new ArrayList<>());map.put("refused",new ArrayList<>());//group类型
        map.get("passed" ).addAll( funGroupService.witchGroupIn(getUserId()));
        map.get("awaited").addAll( funGroupService.witchGroupAwaited(getUserId()));
        map.get("refused").addAll( funGroupService.witchGroupRefused(getUserId()));
        Set<Long> groupIds = new HashSet<>();
        //获取我能管理的群id
        for(FunGroupMember funGroupMember1:list)if(funGroupMember1.getRole().equals("1")||funGroupMember1.getRole().equals("2"))groupIds.add(funGroupMember1.getGroupId());
        //处理别人的请求
        map.put("needToBeProcessed", new ArrayList<>());//user类型
        map.put("refuse", new ArrayList<>());
        List<FunGroupMember> funGroupMembers=funGroupMemberService.witchMemberNeedToBeProcessedWhereGroupIdIn(groupIds);
        for(FunGroupMember funGroupMember:funGroupMembers) map.get("needToBeProcessed").add(funGroupMember.putUserAndClearSensitiveInfo(sysUserService).putGroup(groupService));
        funGroupMembers=funGroupMemberService.witchMemberRefusedWhereGroupIdIn(groupIds);
        for(FunGroupMember funGroupMember:funGroupMembers) map.get("refuse").add(funGroupMember.putUserAndClearSensitiveInfo(sysUserService).putGroup(groupService));
        //填入我在群成员的信息
        for(String i :Arrays.asList("passed","awaited","refused")){
        for(Object obj:map.get(i)){
            FunGroupMember funGroupMember=new FunGroupMember();
            FunGroup funGroup=(FunGroup)obj;
            funGroupMember.setGroupId(funGroup.getGroupId());funGroupMember.setUserId(getUserId());
            funGroup.setGroupMembers(funGroupMemberService.selectFunGroupMemberList(funGroupMember));
        }}


        return AjaxResult.success(map);
    }

}
