package com.totoo.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.totoo.system.service.impl.SysUserServiceImpl;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.totoo.common.annotation.Log;
import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.common.enums.BusinessType;
import com.totoo.system.domain.FunGroupMember;
import com.totoo.system.service.IFunGroupMemberService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 群组成员Controller
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/system/member")
public class FunGroupMemberController extends BaseController
{
    @Autowired
    private IFunGroupMemberService funGroupMemberService;
    @Autowired
    private SysUserServiceImpl sysUserService;

    /**
     * 查询群组成员列表
     */
//    @PreAuthorize("@ss.hasPermi('system:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunGroupMember funGroupMember)
    {
        startPage();
        List<FunGroupMember> list = funGroupMemberService.selectFunGroupMemberList(funGroupMember);
        return getDataTable(list);
    }

    /**
     * 导出群组成员列表
     */
//    @PreAuthorize("@ss.hasPermi('system:member:export')")
    @Log(title = "群组成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunGroupMember funGroupMember)
    {
        List<FunGroupMember> list = funGroupMemberService.selectFunGroupMemberList(funGroupMember);
        ExcelUtil<FunGroupMember> util = new ExcelUtil<FunGroupMember>(FunGroupMember.class);
        util.exportExcel(response, list, "群组成员数据");
    }

    /**
     * 获取群组成员详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:member:query')")
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable("memberId") Long memberId)
    {
        return success(funGroupMemberService.selectFunGroupMemberByMemberId(memberId));
    }

    /**
     * 新增群组成员
     */
//    @PreAuthorize("@ss.hasPermi('system:member:add')")
    @Log(title = "群组成员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunGroupMember funGroupMember)
    {funGroupMember.setUserId(getUserId());
        funGroupMember.setJoinTime(new Date());
        funGroupMember.setRole("3");
        return toAjax(funGroupMemberService.insertFunGroupMember(funGroupMember));
    }

    /**
     * 修改群组成员
     */
//    @PreAuthorize("@ss.hasPermi('system:member:edit')")
    @Log(title = "群组成员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunGroupMember funGroupMember)
    {
        return toAjax(funGroupMemberService.updateFunGroupMember(funGroupMember));
    }
    @PutMapping("/handleRequest")
    public AjaxResult handleRequest(@RequestBody FunGroupMember funGroupMember){
        if(funGroupMember.getRole().equals("0")||funGroupMember.getRole().equals("4")) {
            funGroupMember.setJoinTime(new Date());
            funGroupMemberService.updateFunGroupMember(funGroupMember);
            return AjaxResult.success(funGroupMember);
        }
        return AjaxResult.warn("操作码错误");
    }

    /**
     * 删除群组成员
     */
//    @PreAuthorize("@ss.hasPermi('system:member:remove')")
    @Log(title = "群组成员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{memberIds}")
    public AjaxResult remove(@PathVariable Long[] memberIds)
    {//身份验证，1.删除的成员是自己，2.管理员删除普通成员,3.管理员删除管理员或普通成员
        //1.获取对应的成员2.判断自己是否为此群成员2.1.是的话就删除终止2.2.不是的话获取此用户在此群对应的成员3.根据2.2的群成员角色进行判断4.如果是管理员就删除，如果是普通成员就修改状态为拒绝
        List<Long>newIds= new ArrayList<>();
        for(Long memberId:memberIds){
            FunGroupMember req= funGroupMemberService.selectFunGroupMemberByMemberId(memberId);//1
            if(req.getUserId().equals(getUserId()))continue;//2.1
            FunGroupMember member=new FunGroupMember();//2.2
            member.setUserId(getUserId());member.setGroupId(req.getGroupId());
            member=funGroupMemberService.selectFunGroupMemberList(member).get(0);
            if( member.getRole().equals("3")||member.getRole().equals("4"))continue;//3 role大于等于"3"
            if( req.getRole().compareTo(member.getRole())>0)continue;//req.role<member.role
            newIds.add(memberId);//4
        }

        return toAjax(funGroupMemberService.deleteFunGroupMemberByMemberIds(newIds.toArray(new Long[0])));
    }
    @GetMapping("/getBySelfGroupId")
    public AjaxResult getBySelfGroupId(@RequestParam Long groupId){
        FunGroupMember funGroupMember = new FunGroupMember();
        funGroupMember.setGroupId(groupId);funGroupMember.setUserId(getUserId());
        funGroupMember=funGroupMemberService.selectFunGroupMemberList(funGroupMember).get(0);
        if(funGroupMember!=null)return success(funGroupMember);
        else return error("没有找到该群组");
    }
    @GetMapping("/listByGroupId")
    public AjaxResult listByGroupId(@RequestParam Long groupId){
        List<FunGroupMember> list = funGroupMemberService.witchMemberIsInGroupByGroupId(groupId);
        for(FunGroupMember member:list)member.putUserAndClearSensitiveInfo(sysUserService);
        return success(list);
    }
}
