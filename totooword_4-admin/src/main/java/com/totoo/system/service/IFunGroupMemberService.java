package com.totoo.system.service;

import java.util.List;
import java.util.Set;

import com.totoo.system.domain.FunGroupMember;

/**
 * 群组成员Service接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface IFunGroupMemberService 
{
    /**
     * 查询群组成员
     * 
     * @param memberId 群组成员主键
     * @return 群组成员
     */
    public FunGroupMember selectFunGroupMemberByMemberId(Long memberId);

    /**
     * 查询群组成员列表
     * 
     * @param funGroupMember 群组成员
     * @return 群组成员集合
     */
    public List<FunGroupMember> selectFunGroupMemberList(FunGroupMember funGroupMember);
    public List<FunGroupMember> selectFunGroupMemberListByUserId(Long userId);
    /**
     * 新增群组成员
     * 
     * @param funGroupMember 群组成员
     * @return 结果
     */
    public int insertFunGroupMember(FunGroupMember funGroupMember);

    /**
     * 修改群组成员
     * 
     * @param funGroupMember 群组成员
     * @return 结果
     */
    public int updateFunGroupMember(FunGroupMember funGroupMember);

    /**
     * 批量删除群组成员
     * 
     * @param memberIds 需要删除的群组成员主键集合
     * @return 结果
     */
    public int deleteFunGroupMemberByMemberIds(Long[] memberIds);

    /**
     * 删除群组成员信息
     * 
     * @param memberId 群组成员主键
     * @return 结果
     */
    public int deleteFunGroupMemberByMemberId(Long memberId);

    List<FunGroupMember> selectFunGroupMemberListWhereGroupIdInAndByGroupMember(Set<Long> groupIds, FunGroupMember funGroupMember);
    List<FunGroupMember> witchMemberIsInGroupByUserId(Long userId);
    List<FunGroupMember> witchMemberRefusedWhereGroupIdIn(Set<Long> groupIds);

    List<FunGroupMember> witchMemberNeedToBeProcessedWhereGroupIdIn(Set<Long> groupIds);

    List<FunGroupMember> witchMemberIsHostByUserId(Long userId);

    List<FunGroupMember> witchMemberIsMangerByUserId(Long userId);

    List<FunGroupMember> witchMemberIsInGroupByGroupId(Long groupId);
}
