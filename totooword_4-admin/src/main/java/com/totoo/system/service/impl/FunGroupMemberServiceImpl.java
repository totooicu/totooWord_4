package com.totoo.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.totoo.system.mapper.FunGroupMemberMapper;
import com.totoo.system.domain.FunGroupMember;
import com.totoo.system.service.IFunGroupMemberService;

/**
 * 群组成员Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class FunGroupMemberServiceImpl implements IFunGroupMemberService 
{
    @Autowired
    private FunGroupMemberMapper funGroupMemberMapper;

    /**
     * 查询群组成员
     * 
     * @param memberId 群组成员主键
     * @return 群组成员
     */
    @Override
    public FunGroupMember selectFunGroupMemberByMemberId(Long memberId)
    {
        return funGroupMemberMapper.selectFunGroupMemberByMemberId(memberId);
    }

    /**
     * 查询群组成员列表
     * 
     * @param funGroupMember 群组成员
     * @return 群组成员
     */
    @Override
    public List<FunGroupMember> selectFunGroupMemberList(FunGroupMember funGroupMember)
    {
        return funGroupMemberMapper.selectFunGroupMemberList(funGroupMember);
    }

    @Override
    public List<FunGroupMember> selectFunGroupMemberListByUserId(Long userId) {
        FunGroupMember funGroupMember = new FunGroupMember();
        funGroupMember.setUserId(userId);
        return selectFunGroupMemberList(funGroupMember);
    }

    /**
     * 新增群组成员
     * 
     * @param funGroupMember 群组成员
     * @return 结果
     */
    @Override
    public int insertFunGroupMember(FunGroupMember funGroupMember)
    {
        return funGroupMemberMapper.insertFunGroupMember(funGroupMember);
    }

    /**
     * 修改群组成员
     * 
     * @param funGroupMember 群组成员
     * @return 结果
     */
    @Override
    public int updateFunGroupMember(FunGroupMember funGroupMember)
    {
        return funGroupMemberMapper.updateFunGroupMember(funGroupMember);
    }

    /**
     * 批量删除群组成员
     * 
     * @param memberIds 需要删除的群组成员主键
     * @return 结果
     */
    @Override
    public int deleteFunGroupMemberByMemberIds(Long[] memberIds)
    {
        return funGroupMemberMapper.deleteFunGroupMemberByMemberIds(memberIds);
    }

    /**
     * 删除群组成员信息
     * 
     * @param memberId 群组成员主键
     * @return 结果
     */
    @Override
    public int deleteFunGroupMemberByMemberId(Long memberId)
    {
        return funGroupMemberMapper.deleteFunGroupMemberByMemberId(memberId);
    }
    @Override
    public List<FunGroupMember> selectFunGroupMemberListWhereGroupIdInAndByGroupMember(Set<Long> groupIds, FunGroupMember funGroupMember)
    {
        List<FunGroupMember> funGroupMembers = new ArrayList<>();
        for (Long groupId : groupIds){
            funGroupMember.setGroupId(groupId);
            funGroupMembers.addAll(selectFunGroupMemberList(funGroupMember));
        }
        return funGroupMembers;
    }
    @Override
    public List<FunGroupMember> witchMemberRefusedWhereGroupIdIn(Set<Long> groupIds){
        FunGroupMember funGroupMember = new FunGroupMember();
        funGroupMember.setRole("4");
        return selectFunGroupMemberListWhereGroupIdInAndByGroupMember(groupIds,funGroupMember);
    }

    @Override
    public List<FunGroupMember> witchMemberNeedToBeProcessedWhereGroupIdIn(Set<Long> groupIds){
        FunGroupMember funGroupMember = new FunGroupMember();
        funGroupMember.setRole("3");
        return selectFunGroupMemberListWhereGroupIdInAndByGroupMember(groupIds,funGroupMember);
    }
    @Override
    public List<FunGroupMember> witchMemberIsHostByUserId(Long userId){
        FunGroupMember funGroupMember = new FunGroupMember();
        funGroupMember.setUserId(userId);funGroupMember.setRole("2");
        return selectFunGroupMemberList(funGroupMember);
    }
    @Override
    public List<FunGroupMember> witchMemberIsInGroupByUserId(Long userId){
        return funGroupMemberMapper.witchMemberIsInGroupByUserId(userId);
    }

    @Override
    public List<FunGroupMember> witchMemberIsMangerByUserId(Long userId){
        FunGroupMember funGroupMember = new FunGroupMember();
        funGroupMember.setUserId(userId);funGroupMember.setRole("1");
        return selectFunGroupMemberList(funGroupMember);
    }
    @Override
    public List<FunGroupMember> witchMemberIsInGroupByGroupId(Long groupId){
        return funGroupMemberMapper.witchMemberIsInGroupByGroupId(groupId);
    }
}
