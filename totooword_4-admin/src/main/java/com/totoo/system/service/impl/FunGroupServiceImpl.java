package com.totoo.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.totoo.common.utils.DateUtils;
import com.totoo.system.domain.FunGroupMember;
import com.totoo.system.service.IFunGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunGroupMapper;
import com.totoo.system.domain.FunGroup;
import com.totoo.system.service.IFunGroupService;

/**
 * 群组Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class FunGroupServiceImpl implements IFunGroupService 
{
    @Autowired
    private FunGroupMapper funGroupMapper;
    @Autowired
    private IFunGroupMemberService funGroupMemberService;

    /**
     * 查询群组
     * 
     * @param groupId 群组主键
     * @return 群组
     */
    @Override
    public FunGroup selectFunGroupByGroupId(Long groupId)
    {
        return funGroupMapper.selectFunGroupByGroupId(groupId);
    }

    /**
     * 查询群组列表
     * 
     * @param funGroup 群组
     * @return 群组
     */
    @Override
    public List<FunGroup> selectFunGroupList(FunGroup funGroup)
    {
        return funGroupMapper.selectFunGroupList(funGroup);
    }

    /**
     * 新增群组
     * 
     * @param funGroup 群组
     * @return 结果
     */
    @Override
    public int insertFunGroup(FunGroup funGroup)
    {
        funGroup.setCreateTime(DateUtils.getNowDate());
        return funGroupMapper.insertFunGroup(funGroup);
    }

    /**
     * 修改群组
     * 
     * @param funGroup 群组
     * @return 结果
     */
    @Override
    public int updateFunGroup(FunGroup funGroup)
    {
        return funGroupMapper.updateFunGroup(funGroup);
    }

    /**
     * 批量删除群组
     * 
     * @param groupIds 需要删除的群组主键
     * @return 结果
     */
    @Override
    public int deleteFunGroupByGroupIds(Long[] groupIds)
    {
        return funGroupMapper.deleteFunGroupByGroupIds(groupIds);
    }

    /**
     * 删除群组信息
     * 
     * @param groupId 群组主键
     * @return 结果
     */
    @Override
    public int deleteFunGroupByGroupId(Long groupId)
    {
        return funGroupMapper.deleteFunGroupByGroupId(groupId);
    }

    @Override
    public List<FunGroup> witchGroupIn(Long userId) {
        List<FunGroupMember> list = funGroupMemberService.selectFunGroupMemberListByUserId(userId);
        List<FunGroup> funGroups = new ArrayList<>();
        for(FunGroupMember funGroupMember1:list)switch (funGroupMember1.getRole()) {case "0":case "1":case "2":funGroups.add(selectFunGroupByGroupId(funGroupMember1.getGroupId()));}
        return funGroups;
    }
    @Override
    public List<FunGroup> witchGroupAwaited(Long userId ){
        List<FunGroupMember> list = funGroupMemberService.selectFunGroupMemberListByUserId(userId);
        List<FunGroup> funGroups = new ArrayList<>();
        for(FunGroupMember funGroupMember1:list) if ("3".equals(funGroupMember1.getRole())) funGroups.add(selectFunGroupByGroupId(funGroupMember1.getGroupId()));
        return funGroups;
    }
    @Override
    public List<FunGroup> witchGroupRefused(Long userId){
        List<FunGroupMember> list = funGroupMemberService.selectFunGroupMemberListByUserId(userId);
        List<FunGroup> funGroups = new ArrayList<>();
        for(FunGroupMember funGroupMember1:list) if ("4".equals(funGroupMember1.getRole())) funGroups.add(selectFunGroupByGroupId(funGroupMember1.getGroupId()));
        return funGroups;
    }
    @Override
    public List<FunGroup> getHostGroupsByUserId(Long userId){
        List<FunGroupMember> list = funGroupMemberService.witchMemberIsHostByUserId(userId);
        List<FunGroup> funGroups = new ArrayList<>();
        for(FunGroupMember funGroupMember1:list) funGroups.add(selectFunGroupByGroupId(funGroupMember1.getGroupId()));
        return funGroups;
    }
    @Override
    public List<FunGroup> witchGroupUserIsManager(Long userId){
        List<FunGroupMember> list = funGroupMemberService.witchMemberIsMangerByUserId(userId);
        List<FunGroup> funGroups = new ArrayList<>();
        for(FunGroupMember funGroupMember1:list) funGroups.add(selectFunGroupByGroupId(funGroupMember1.getGroupId()));
        return funGroups;
    }

    @Override
    public List<FunGroup> witchGroupUserCanManage(Long userId){
        List<FunGroupMember> list = funGroupMemberService.witchMemberIsMangerByUserId(userId);
        if(list.size()==0) list = new ArrayList<>();
        list.addAll(funGroupMemberService.witchMemberIsHostByUserId(userId));

        List<FunGroup> funGroups = new ArrayList<>();
        for(FunGroupMember funGroupMember1:list) funGroups.add(selectFunGroupByGroupId(funGroupMember1.getGroupId()));
        return funGroups;
    }


}
